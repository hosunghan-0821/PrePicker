package co.kr.demo.service.dto.domainDto;

import co.kr.demo.domain.model.OptionDetail;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
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


    public static OptionDetailDto toOptionDetailDtoByViewDto(OptionViewDto optionViewDto) {

        return OptionDetailDto
                .builder()
                .optionValue(optionViewDto.getOptionValue())
                .build();
    }

    public static OptionDetailDto of(OptionDetail optionDetail) {
        return OptionDetailDto.builder()
                .optionDetailId(optionDetail.getId())
                .optionValue(optionDetail.getOptionValue())
                .build();
    }
}
