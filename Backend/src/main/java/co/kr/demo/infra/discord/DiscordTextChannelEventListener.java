package co.kr.demo.infra.discord;

import co.kr.demo.infra.sms.SMSMessageBuilder;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.order.Interface.IOrderFacade;
import co.kr.demo.service.order.OrderFacadeImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordTextChannelEventListener extends ListenerAdapter {

    private final IOrderFacade orderFacade;
    private final SMSMessageBuilder smsMessageBuilder;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        //Channel Validation
        if (!event.isFromType(ChannelType.TEXT) || event.getAuthor().isBot()) {
            return;
        }
        User user = event.getAuthor();
        final Message message = event.getMessage();
        String plainMessage = event.getMessage().getContentDisplay();

        //채널방 분리
        final TextChannel textChannel = event.getChannel().asTextChannel();

        //후에 Interface 리팩토링 고려하자.
        final String channelName = textChannel.getName();

        log.info("CHANNEL NAME \t: " + channelName);
        log.info("SEND USER \t : " + user.getName());
        log.info("GET MESSAGE \t : " + plainMessage);


        if (channelName.equals(EDiscordChannel.ORDER_VIEW_ROOM.getChannelName())) {

            if (message.getContentDisplay().startsWith("!")) {
                plainMessage = plainMessage.replace("!", "").trim().replaceAll(" ", "");
                returnMessageToChannel(textChannel, plainMessage);
            }

        }


        // 텍스트 채널


    }


    private void returnMessageToChannel(TextChannel textChannel, String receivedMessage) {

        /*
         * 주문정보 Return 하는 것 만들기
         *
         * */

        String returnMessage = "";
        switch (receivedMessage) {

            case "전체":
                returnMessage = " 기능 준비중입니다 :)";
                break;
            case "당일":
                returnMessage = " 기능 준비중입니다 :)";

            default://단건 조회
                try {
                    final String[] info = receivedMessage.split("/");
                    System.out.println(info[0] +" /" +info[1]);
                    final OrderViewDto orderViewDto = orderFacade.getOrderDetail(OrderViewDto.builder().clientName(info[0]).clientPhoneNum(info[1]).build());
                    final String productInfo = smsMessageBuilder.makeProductInfoWithProductInfoList(orderViewDto.getProducts());
                    final String reservationDate = smsMessageBuilder.makeReservationDate(orderViewDto.getReservationDate());
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("주문 조회");
                    embedBuilder.addField("기본 정보",orderViewDto.toString()+"주문일시 : "+reservationDate,false);
                    embedBuilder.addField("상품 주문 정보", productInfo, true);
                    /*
                    * TO-DO
                    * 주문 옵션상세 추가로 뿌려줘야함
                    * */

                    textChannel.sendMessageEmbeds(embedBuilder.build()).queue();

                } catch (Exception e) {
                    e.printStackTrace();
                    textChannel.sendMessage("형식 오류").queue();
                }
                break;
        }
    }
}
