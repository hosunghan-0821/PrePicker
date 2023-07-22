package co.kr.demo.service.dto.viewDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionViewDto {

    @JsonProperty("optionId")
    private Long optionId;
    @JsonProperty("name")
    private String optionName;
    @JsonProperty("optionDetailId")
    private Long optionDetailId;
    @JsonProperty("value")
    private String optionValue;
    @JsonProperty("fee")
    private Long fee;


}
