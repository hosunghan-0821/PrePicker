package co.kr.demo.api.admin;

import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.infra.discord.DiscordBot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class testController {

    private final DiscordBot discordBot;
    @GetMapping("/sms-test")
    public void SMSTest(){
        throw new NotFoundException();
    }

    @GetMapping("/discord-test")
    public void DISCORDTest(){
        discordBot.sendMessage("일반","텍스트 보내기 테스트");
    }
}
