package co.kr.demo.api;

import co.kr.demo.global.exception.ErrorCode;
import co.kr.demo.global.exception.NotFoundException;
import co.kr.demo.infra.sms.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class testController {

    @GetMapping("/sms-test")
    public void SMSTest(){
        throw new NotFoundException();
    }
}
