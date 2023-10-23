package co.kr.demo.infra.sms;

import co.kr.demo.global.config.ApplicationOptionConfig;
import co.kr.demo.global.error.exception.InternalServerException;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SMSService {
    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    private final ObjectMapper objectMapper;

    private final SMSMessageBuilder smsMessageBuilder;

    private final ApplicationOptionConfig applicationOptionConfig;

    @Async
    public void sendMessage(List<SMSMessageDto> SMSMessageDtoList) {

        if(!applicationOptionConfig.isSMSService()){
            log.info("SMS Service off");
            return;
        }
        final RestTemplate rt = new RestTemplate();
        final HttpHeaders headers = makeSMSRequestHeader();
        final SMSRequestDto smsRequestDto = makeSMSRequestDto(SMSMessageDtoList);

        try {
            String body = objectMapper.writeValueAsString(smsRequestDto);
            HttpEntity<String> request = new HttpEntity<>(body, headers);

            final ResponseEntity<SMSResponseDto> response = rt.exchange("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages", HttpMethod.POST, request, SMSResponseDto.class);
            //성공 할 때도 문자 발송이 되는 것이기 때문에 개인적으로 Notice해줄 수 있어야 함.
            log.info("SMS 발신 성공");

        } catch (Exception e) {
            /*
             * TO-DO
             * 응답받아서 Status-CODE 보고 난후 Exception 처리를 하든 뭘 하든 해야함
             * */
            e.printStackTrace();
            throw new InternalServerException();
        }


    }

    public <T> SMSMessageDto makeSMSMessage(T data, SMSMessageType smsMessageType) {

        switch (smsMessageType) {
            case ORDER_CONFIRM:
                if (data instanceof OrderViewDto) {
                    OrderViewDto orderViewDto = (OrderViewDto) data;
                    SMSMessageDto smsMessageDto = new SMSMessageDto();
                    smsMessageDto.setTo(orderViewDto.getClientPhoneNum().replaceAll("-", ""));
                    smsMessageDto.setContent(smsMessageBuilder.makeMessageOrderConfirm(orderViewDto));
                    log.info(smsMessageDto.getContent());
                    return smsMessageDto;
                }

                break;
            case ORDER_CANCEL:
                if(data instanceof OrderViewDto){
                    OrderViewDto orderViewDto = (OrderViewDto) data;
                    SMSMessageDto smsMessageDto = new SMSMessageDto();
                    smsMessageDto.setTo(orderViewDto.getClientPhoneNum().replaceAll("-", ""));
                    smsMessageDto.setContent(smsMessageBuilder.makeMessageOrderCancel(orderViewDto));
                    log.info(smsMessageDto.getContent());
                    return smsMessageDto;
                }
                break;
            default:
                break;
        }

        return null;
    }

    private SMSRequestDto makeSMSRequestDto(List<SMSMessageDto> smsMessageDtoList) {
        return SMSRequestDto.builder()
                .type("LMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("[파리바게트 다이아몬드 광장점]")
                .messages(smsMessageDtoList)
                .build();

    }

    private HttpHeaders makeSMSRequestHeader() {

        Long time = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSMSRequestHeaderSignature(time));

        return headers;
    }

    private String makeSMSRequestHeaderSignature(Long time) {

        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            String encodeBase64String = Base64.encodeBase64String(rawHmac);

            return encodeBase64String;
        } catch (Exception e) {
            /*
             *
             * TO-DO Exception 처리 필요
             *
             * */
            throw new RuntimeException();
        }

    }
}
