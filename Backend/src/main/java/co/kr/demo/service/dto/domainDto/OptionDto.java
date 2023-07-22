package co.kr.demo.service.dto.domainDto;

import co.kr.demo.domain.model.enumeration.EOptionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {

    @JsonProperty("id")
    private Long optionId;

    @JsonProperty("name")
    private String optionName;

    @JsonProperty("type")
    private EOptionType eOptionType;
    @JsonProperty("additionalData")
    private String additionalData;


}
