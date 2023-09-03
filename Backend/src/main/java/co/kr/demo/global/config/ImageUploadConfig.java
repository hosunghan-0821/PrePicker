package co.kr.demo.global.config;

import co.kr.demo.service.image.Interface.IImageUpload;
import co.kr.demo.service.image.S3ImageUploadImpl;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ImageUploadConfig {

    private final AmazonS3Client amazonS3Client;

    private final static String DEFAULT_DIRECTORY_PATH="PICKER_PENG/";

    @Bean
    public IImageUpload imageUpload() {
        return new S3ImageUploadImpl(amazonS3Client,DEFAULT_DIRECTORY_PATH);
    }
}
