package co.kr.demo.api.admin;

import co.kr.demo.global.config.ApplicationOptionConfig;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ApplicationOptionConfig applicationOptionConfig;

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
        try {
            File file = new File(multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            CSVReader csvReader = new CSVReader(new FileReader(file));
            List<String[]> lines = csvReader.readAll();
            lines.forEach(line -> System.out.println(String.join(",", line)));
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
