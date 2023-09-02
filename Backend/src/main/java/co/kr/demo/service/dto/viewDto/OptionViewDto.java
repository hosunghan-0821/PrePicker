package co.kr.demo.service.dto.viewDto;


import co.kr.demo.domain.model.enumeration.EOptionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionViewDto {


    //Domain :  Option 항목
    @JsonProperty("optionId")
    private Long optionId;
    @JsonProperty("name")
    private String optionName;

    @JsonProperty("type")
    private EOptionType eOptionType;

    @JsonProperty("additionalData")
    private String additionalData;

    //Domain : OptionDetail 항목
    @JsonProperty("optionDetailId")
    private Long optionDetailId;
    @JsonProperty("value")
    private String optionValue;
    @JsonProperty("fee")
    private Long fee;


}
