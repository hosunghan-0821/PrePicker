package co.kr.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;

    public static <T> ResponseDto<T> response(T data) {
        return ResponseDto.<T>builder()
                .data(data)
                .success(true)
                .build();
    }
}
