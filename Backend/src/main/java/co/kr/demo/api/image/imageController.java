package co.kr.demo.api.image;

import co.kr.demo.service.dto.ResponseDto;
import co.kr.demo.service.image.Interface.IImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class imageController {


    private final IImageUpload S3ImageUploader;


    @PostMapping("/images")
    public ResponseEntity<ResponseDto<?>> registerImages(@RequestPart(value = "files", required = false) List<MultipartFile> multipartFileList) throws IOException {
        final List<String> savedPath = S3ImageUploader.uploadImage(multipartFileList);
        return ResponseEntity.ok(ResponseDto.response(ResponseDto.response(savedPath)));

    }
}
