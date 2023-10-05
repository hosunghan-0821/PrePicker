package co.kr.demo.infra.sms;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class MessageDto {
    private String to;
    private String content;
}
