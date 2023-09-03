package co.kr.demo.service.image;

import co.kr.demo.service.image.Interface.IImageUpload;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class S3ImageUploadImpl implements IImageUpload {

    private final AmazonS3Client amazonS3Client;

    private String directoryPath;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public S3ImageUploadImpl(AmazonS3Client amazonS3Client, String directoryPath) {
        this.amazonS3Client = amazonS3Client;
        this.directoryPath = directoryPath;
    }

    @Override
    public List<String> uploadImage(List<MultipartFile> multipartFileList) {

        List<String> uploadPaths = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {

            String fileOriginalFilename = multipartFile.getOriginalFilename();
            String fileName = fileOriginalFilename.substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
            String fileExtension = fileOriginalFilename.substring(multipartFile.getOriginalFilename().lastIndexOf("."));

            fileExtension = fileExtension.toUpperCase();

            if (fileExtension.equals(".PNG") || fileExtension.equals(".JPEG") || fileExtension.equals(".JPG") || fileExtension.equals(".BMP")) {
                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                File convertFile = new File(System.getProperty("user.dir") + "/" + fileName + "_" + now + fileExtension);
                try {
                    if (convertFile.createNewFile()) {
                        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                            fos.write(multipartFile.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileName = directoryPath + convertFile.getName();
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));

                final String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();
                uploadPaths.add(uploadImageUrl);
                if (convertFile.exists()) {
                    if (!convertFile.delete()) {
                        log.error("임시 파일 삭제 실패 : {}", fileName);
                    }
                }

            } else {
                log.error("IMAGE EXTETNSION ERROR filename: {}, fileExtension {} ", fileName, fileExtension);
            }
        }
        return uploadPaths;
    }

    @Override
    public void setDirectoryPath(String directoryPath) {
        if (!directoryPath.endsWith("/")) {
            directoryPath = directoryPath + "/";
        }
        this.directoryPath = directoryPath;
    }

}
