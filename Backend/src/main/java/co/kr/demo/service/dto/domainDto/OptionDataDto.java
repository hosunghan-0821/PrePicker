package co.kr.demo.service.dto.domainDto;


import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.OptionData;
import co.kr.demo.domain.model.enumeration.EOptionDataType;
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
public class OptionDataDto {

    @JsonProperty("id")
    private Long optionDataId;

    @JsonProperty("optionDataName")
    private String optionDataName;

    @JsonProperty("optionData")
    private String optionData;

    @JsonProperty("optionDataType")
    private EOptionDataType optionDataType;


    public static OptionData  toOptionData(Option option, OptionDataDto optionDataDto) {
        return OptionData.builder()
                .option(option)
                .optionDataName(optionDataDto.getOptionDataName())
                .optionData(optionDataDto.getOptionData())
                .eOptionDataType(optionDataDto.getOptionDataType())
                .build();
    }
}
