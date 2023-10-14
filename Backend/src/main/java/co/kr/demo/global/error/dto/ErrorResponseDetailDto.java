package co.kr.demo.global.error.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Setter(AccessLevel.PROTECTED)
@Getter
@SuperBuilder
public class ErrorResponseDetailDto extends ErrorResponseDto {

    private List<ErrorDetail> errorDetailList;

    public ErrorResponseDetailDto(String ErrorCode, String Message) {

        super(ErrorCode, Message);
        errorDetailList = new ArrayList<>();
    }

    public ErrorResponseDetailDto() {
        super();
        errorDetailList = new ArrayList<>();
    }


    public void addErrorDetail(String errorObject, String errorDetailMessage) {
        this.errorDetailList.add(new ErrorDetail(errorObject, errorDetailMessage));
    }

    @Getter
    @Setter
    class ErrorDetail {
        private String errorObject;
        private String errorDetailMessage;

        private ErrorDetail(String errorObject, String errorDetailMessage) {
            this.errorObject = errorObject;
            this.errorDetailMessage = errorDetailMessage;
        }
    }
}
