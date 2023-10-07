package co.kr.demo.global.exception;

import lombok.*;

@Setter(AccessLevel.PROTECTED)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String ErrorCode;
    private String Message;
}
