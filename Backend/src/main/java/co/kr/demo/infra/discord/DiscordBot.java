package co.kr.demo.infra.discord;

import lombok.RequiredArgsConstructor;
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
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordBot implements ApplicationRunner {

    private final Map<String,String> channelHashMap= new HashMap<>();
    private JDA jda;
    private final DiscordTextChannelEventListener discordTextChannelEventListener;

    private final DiscordChannel discordChannel;
    @Value("${discord.bot.token}")
    private String discordBotToken;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jda = JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("서버 실행중"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(discordTextChannelEventListener)
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<TextChannel> textChannels = jda.getTextChannels();
        discordChannel.updateChannelData(textChannels);
    }
}
