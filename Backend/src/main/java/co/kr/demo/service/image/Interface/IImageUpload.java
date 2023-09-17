package co.kr.demo.service.image.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IImageUpload {

     List<String> uploadImage (List<MultipartFile> multipartFileList);

     void setDirectoryPath(String directoryPath);
}
