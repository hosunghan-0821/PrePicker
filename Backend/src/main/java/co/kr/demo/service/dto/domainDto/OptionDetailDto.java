package co.kr.demo.service.dto.domainDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionDetailDto {

    @JsonProperty("id")
    private Long optionDetailId;
    @JsonProperty("value")
    private String optionValue;
    @JsonProperty("fee")
    private Long fee;
}
