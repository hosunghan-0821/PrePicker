package co.kr.demo.service.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OptionDetailDto {

    @JsonProperty("id")
    private Long optionDetailId;
    @JsonProperty("optionName")
    private Long optionName;
    @JsonProperty("value")
    private String optionValue;
    @JsonProperty("fee")
    private Long fee;
}
