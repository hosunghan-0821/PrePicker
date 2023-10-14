package co.kr.demo.api.admin;

import co.kr.demo.global.config.ApplicationOptionConfig;
import co.kr.demo.service.product.ProductService;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ApplicationOptionConfig applicationOptionConfig;

    private final ProductService productService;
    @PostMapping("/SMSService")
    public ResponseEntity<?> toggleSMSService() {
        applicationOptionConfig.setSMSService(!applicationOptionConfig.isSMSService());
        return ResponseEntity.ok(applicationOptionConfig.isSMSService());
    }

    @PostMapping("/products/csv-list")
    public void registerProductsWithCSV(@RequestPart(value = "file") MultipartFile multipartFile) {


        if (!Objects.requireNonNull(multipartFile.getOriginalFilename()).endsWith(".csv")) {
            throw new IllegalArgumentException();
        }
        productService.registerProductWithCSV(multipartFile);



    }

}
