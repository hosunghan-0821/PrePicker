package co.kr.demo.infra.discord;

import co.kr.demo.infra.sms.SMSMessageBuilder;
import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.order.Interface.IOrderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


        switch (receivedMessage) {

            case "전체":
                textChannel.sendMessage("기능 준비중입니다 :)").queue();
                break;
            case "날짜검색":
                receivedMessage = receivedMessage.replace("날짜검색", "");
                SearchConditionDto.SearchConditionDtoBuilder builder = SearchConditionDto.builder();
                Page<OrderViewDto> orderViewDtoPage;
                if (receivedMessage.contains("/")) {
                    final String[] info = receivedMessage.split("/");

                    SearchConditionDto searchConditionDto = builder
                            .startDate(SearchConditionDto.toParseInstant(info[0], SearchConditionDto.START_DATE))
                            .endDate(SearchConditionDto.toParseInstant(info[2], SearchConditionDto.END_DATE))
                            .build();

                    orderViewDtoPage = orderFacade.getOrderList(PageRequest.of(0, 100), searchConditionDto);

                } else {
                    SearchConditionDto searchConditionDto = builder.startDate(SearchConditionDto.toParseInstant(receivedMessage, SearchConditionDto.START_DATE))
                            .endDate(SearchConditionDto.toParseInstant(receivedMessage, SearchConditionDto.END_DATE)).build();
                    orderViewDtoPage = orderFacade.getOrderList(PageRequest.of(0, 100), searchConditionDto);
                }

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("주문 조회");
                orderViewDtoPage.getContent().stream().map(this::makeEmbedMessageField)
                        .forEach(v -> {
                                    for (MessageEmbed.Field field : v) {
                                        embedBuilder.addField(field);
                                    }
                                }
                        );
                textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
                break;
            case "사용법":
                break;
            default://단건 조회
                try {
                    final String[] info = receivedMessage.split("/");
                    final OrderViewDto orderViewDto = orderFacade.getOrderDetail(OrderViewDto.builder().clientName(info[0]).clientPhoneNum(info[1]).build());


                    embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("주문 조회");
                    List<MessageEmbed.Field> fieldList = makeEmbedMessageField(orderViewDto);
                    for (MessageEmbed.Field field : fieldList) {
                        embedBuilder.addField(field);
                    }
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

    @Override
    public void onGuildReady(GuildReadyEvent event) {

        List<CommandData> commandData = new ArrayList<>();
        List<OptionData>optionDataList = new ArrayList<>();

        OptionData optionDataStart = new OptionData(OptionType.STRING, "start", "다음 형식으로 보내주세요 ex)2023-07-22",true);
        OptionData optionDataEnd = new OptionData(OptionType.STRING, "end", "다음 형식으로 보내주세요 ex)2023-07-22",true);
        optionDataList.add(optionDataStart);
        optionDataList.add(optionDataEnd);

        commandData.add(Commands.slash("주문목록", "시작날짜,나중날짜를 입력해주세요 ex)2023-02-28 형식주의!").addOptions(optionDataList));
        event.getGuild().updateCommands().addCommands(commandData).queue();

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();

        if (command.equals("주문목록")) {
            String startDate = event.getOption("start").getAsString();
            String endDate =event.getOption("end").getAsString();
            SearchConditionDto.SearchConditionDtoBuilder builder = SearchConditionDto.builder();
            Page<OrderViewDto> orderViewDtoPage;

            SearchConditionDto searchConditionDto = builder
                    .startDate(SearchConditionDto.toParseInstant(startDate, SearchConditionDto.START_DATE))
                    .endDate(SearchConditionDto.toParseInstant(endDate, SearchConditionDto.END_DATE))
                    .build();

            orderViewDtoPage = orderFacade.getOrderList(PageRequest.of(0, 100), searchConditionDto);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("주문 조회");
            orderViewDtoPage.getContent().stream().map(this::makeEmbedMessageField)
                    .forEach(v -> {
                                for (MessageEmbed.Field field : v) {
                                    embedBuilder.addField(field);
                                }
                            }
                    );
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            event.reply("Your Message was sent!").queue();
        }


    }

    private List<MessageEmbed.Field> makeEmbedMessageField(OrderViewDto orderViewDto) {


        final String productInfo = smsMessageBuilder.makeProductInfoWithProductInfoList(orderViewDto.getProducts());
        final String reservationDate = smsMessageBuilder.makeReservationDate(orderViewDto.getReservationDate());

        List<MessageEmbed.Field> fieldList = new ArrayList<>();
        fieldList.add(new MessageEmbed.Field("기본 정보", orderViewDto.toString() + "주문일시 : " + reservationDate, false));
        fieldList.add(new MessageEmbed.Field("상품 주문 정보", productInfo, true));

        return fieldList;


    }
}
