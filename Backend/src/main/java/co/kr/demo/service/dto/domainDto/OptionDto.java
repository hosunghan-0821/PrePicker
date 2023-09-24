package co.kr.demo.service.dto.domainDto;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.enumeration.EOptionType;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
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


    public static OptionDto toOptionDtoByViewDto(OptionViewDto optionViewDto){
        return OptionDto.builder()
                .optionId(optionViewDto.getOptionId())
                .optionName(optionViewDto.getOptionName())
                .eOptionType(optionViewDto.getEOptionType())
                .additionalData(optionViewDto.getAdditionalData())
                .build();
    }

    public static OptionViewDto toOptionViewDtoByOptionDto(OptionDto optionDto){
        return OptionViewDto.builder()
                .optionId(optionDto.getOptionId())
                .eOptionType(optionDto.getEOptionType())
                .optionName(optionDto.getOptionName())
                .build();
    }

    public static OptionViewDto toOptionViewDtoByOptionDtoAndOptionDetailDto(OptionDto optionDto,OptionDetailDto optionDetailDto){
        return OptionViewDto.builder()
                .optionId(optionDto.getOptionId())
                .eOptionType(optionDto.getEOptionType())
                .optionName(optionDto.getOptionName())
                .optionDetailId(optionDetailDto.getOptionDetailId())
                .optionValue(optionDetailDto.getOptionValue())
                .fee(optionDetailDto.getFee())
                .build();
    }

    public static Option toOption(OptionDto optionDto){
        return Option.builder()
                .optionName(optionDto.getOptionName())
                .eOptionType(optionDto.getEOptionType())
                .additionalData(optionDto.getAdditionalData())
                .build();
    }

    public static OptionDto of(Option option){
        return OptionDto.builder()
                .optionId(option.getId())
                .optionName(option.getOptionName())
                .eOptionType(option.getEOptionType())
                .additionalData(option.getAdditionalData())
                .build();
    }


}
