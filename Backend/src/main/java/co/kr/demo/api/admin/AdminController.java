package co.kr.demo.api.admin;

import co.kr.demo.global.config.ApplicationOptionConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ApplicationOptionConfig applicationOptionConfig;
    @PostMapping("/SMSService")
    public ResponseEntity<?> toggleSMSService(){
        applicationOptionConfig.setSMSService(!applicationOptionConfig.isSMSService());
        return ResponseEntity.ok(applicationOptionConfig.isSMSService());
    }
}
