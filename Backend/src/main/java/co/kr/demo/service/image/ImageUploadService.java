package co.kr.demo.service.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public void test(MultipartFile multipartFile) throws IOException {

        String fileOriginalFilename = multipartFile.getOriginalFilename();
        String filename = fileOriginalFilename.substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
        String fileExtension = fileOriginalFilename.substring(multipartFile.getOriginalFilename().lastIndexOf("."));


        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File convertFile = new File(System.getProperty("user.dir") + "/" + filename + "_" + now + fileExtension);

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String directoryPath = "test";
        String fileName = directoryPath + "/" + convertFile.getName();

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));
        final String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        System.out.println("업로드 경로 : "+ uploadImageUrl);
        if (convertFile.exists()) {
            if (convertFile.delete()) {
                System.out.println("file 삭제 성공");
            } else {
                System.out.println("file 삭제 실패");
            }
        }


    }
}
