package co.kr.demo.service.dto.domainDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter(AccessLevel.PROTECTED)
@SuperBuilder
public class OptionDataADto extends OptionDataDto{
    @JsonProperty("anotherInfo")
    private String anotherInfo;
    public OptionDataADto(Long optionDataId, String optionDataName, String optionData,String anotherInfo) {
        super(optionDataId, optionDataName, optionData);
        this.anotherInfo=anotherInfo;
    }

    public OptionDataADto() {
    }
}
