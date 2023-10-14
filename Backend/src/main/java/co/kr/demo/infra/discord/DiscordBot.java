package co.kr.demo.infra.discord;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Slf4j
@Component
public class DiscordBot extends ListenerAdapter {

    @Value("${discord.bot.token}")
    private String discordBotToken;

    @PostConstruct
    public void init() {
        JDA jda = JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("서버 실행중"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(this)
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        String plainMessage = message.getContentDisplay();
        log.info("get Message : " + message.getContentDisplay());
        if (user.isBot()) {
            return;
        } else {
            if (message.getContentDisplay().startsWith("!")) {
                plainMessage = plainMessage.replace("!", "");
                String returnMessage = makeReturnMessage(event, plainMessage);
                textChannel.sendMessage(returnMessage).queue();
            }
        }
    }


    private String makeReturnMessage(MessageReceivedEvent event, String message) {

        User user = event.getAuthor();
        String returnMessage = "";
        switch (message) {
            case "이름":
                returnMessage = user.getName();
                break;
            case "태그":
                returnMessage = user.getAsTag();
                break;
            case "테스트":
                returnMessage = user.getAsMention();
                break;
            default:
                returnMessage = "없는 명령어 입니다 :(";
                break;
        }
        return returnMessage;
    }
}
