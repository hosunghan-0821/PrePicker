package co.kr.demo.infra.discord;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Slf4j
@Component
public class DiscordBot extends ListenerAdapter {

    private Map<String,String> channelHashMap= new HashMap<>();
    private JDA jda;
    @Value("${discord.bot.token}")
    private String discordBotToken;

    @PostConstruct
    public void init() throws IOException {
         jda = JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("서버 실행중"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)

                .addEventListeners(this)
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<TextChannel> textChannels = jda.getTextChannels();
        //In-Memory로 들고 있는게 낫지 않은가?
        for(TextChannel textChannel: textChannels){

            log.info("ID :" +  textChannel.getId()+"NAME: "+ textChannel.getName() );
            channelHashMap.put(textChannel.getId(),textChannel.getName());

            Button primary = Button.primary("hello", "click me");
            Button naver = Button.link("https://www.youtube.com/", "Naver");

            LayoutComponent layoutComponent = ActionRow.of(naver);
            MessageEmbed embed = new MessageEmbed(
                    null,
                    "신제품 ~~~~~",
                    "내용 12ㅁ3221312313123132131",
                    null,
                    null,
                    65280,
                    new MessageEmbed.Thumbnail("https://hosunghan2.s3.ap-northeast-2.amazonaws.com/PICKER_PENG/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202023-01-06%20155856_20230916_144431.PNG",null,200,200),
                    new MessageEmbed.Provider("유튜브","https://www.youtube.com/"),
                    null,
                    null,
                    new MessageEmbed.Footer("하단",null,null),
                    null,
                    null);

            textChannel.sendMessageEmbeds(embed).addComponents(layoutComponent).queue();



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

    public void sendMessage(){

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
