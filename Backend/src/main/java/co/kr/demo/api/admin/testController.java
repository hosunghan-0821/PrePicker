package co.kr.demo.api.admin;

import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.infra.discord.DiscordBot;
import co.kr.demo.infra.discord.EDiscordChannel;
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
