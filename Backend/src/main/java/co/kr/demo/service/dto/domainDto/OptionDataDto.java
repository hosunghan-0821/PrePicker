package co.kr.demo.service.dto.domainDto;


import co.kr.demo.util.serializer.OptionDataDtoDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter(AccessLevel.PROTECTED)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = OptionDataDtoDeserializer.class)
public class OptionDataDto {

    @JsonProperty("id")
    private Long optionDataId;

    @JsonProperty("optionDataName")
    private String optionDataName;

    @JsonProperty("optionData")
    private String optionData;



}
