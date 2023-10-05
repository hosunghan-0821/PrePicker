package co.kr.demo.infra.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
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

    public void sendMessage() {

        try{
            Long time = System.currentTimeMillis();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", time.toString());
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

            RestTemplate rt = new RestTemplate();

            List<MessageDto> messages = new ArrayList<>();

            MessageDto messageDto = MessageDto.builder()
                    .content("문자메시지 테스트 \n 사랑해욤 히히")
                    .to("01039942490")
                    .build();

            messages.add(messageDto);

            SMSRequestDto smsRequestDto = SMSRequestDto.builder()
                    .type("SMS")
                    .contentType("COMM")
                    .countryCode("82")
                    .from(phone)
                    .content(messageDto.getContent())
                    .messages(messages)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(smsRequestDto);

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            final ResponseEntity<String> exchange = rt.exchange("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages", HttpMethod.POST, request, String.class);
            System.out.println(exchange);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

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

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }
}
