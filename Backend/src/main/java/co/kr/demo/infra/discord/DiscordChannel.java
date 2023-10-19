package co.kr.demo.infra.discord;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordChannel {

    private final Map<String,TextChannel> channelHashMap= new HashMap<>();

    public void updateChannelData(List<TextChannel> textChannelList) {

        //In-Memory로 들고 있는게 낫지 않은가?
        for(TextChannel textChannel: textChannelList){
            log.info("ID :" +  textChannel.getId()+"NAME: "+ textChannel.getName() );
            channelHashMap.put(textChannel.getName(),textChannel);
        }
    }

    public TextChannel getChannelId(EDiscordChannel discordChannel){
        return channelHashMap.get(discordChannel.getChannelName());
    }


    public void sendMessageToChannel(EDiscordChannel eDiscordChannel,String textMessage){

        final TextChannel textChannel =  getChannelId(eDiscordChannel);
        if(textChannel!=null){
            textChannel.sendMessage(textMessage).queue();
        }
        else{
            log.error("유효하지 않은 채널이름 : {}",eDiscordChannel.getChannelName());
        }
    }
}
