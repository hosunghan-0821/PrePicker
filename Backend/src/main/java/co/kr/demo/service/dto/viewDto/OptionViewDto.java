package co.kr.demo.service.dto.viewDto;


import co.kr.demo.domain.model.enumeration.EOptionType;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnCreateProduct;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnRegisterOrder;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnUpdateProduct;
import co.kr.demo.service.dto.domainDto.OptionDataDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.util.serializer.OptionDataDtoDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionViewDto {


    //Domain :  Option 항목
    @NotNull(groups = OnCreateProduct.class)
    @NotNull(groups = OnUpdateProduct.class)
    @NotNull(groups = OnRegisterOrder.class)
    @JsonProperty("optionId")
    private Long optionId;


    @NotNull(groups = OnUpdateProduct.class)
    @JsonProperty("name")
    private String optionName;


    @NotNull(groups = OnUpdateProduct.class)
    @JsonProperty("type")
    private EOptionType eOptionType;


    @JsonProperty("optionDataList")
    private List<OptionDataDto> optionDataList;



    @NotNull(groups = OnUpdateProduct.class)
    @JsonProperty("fee")
    private Long fee;

    //Domain : OptionDetail 항목
    @JsonProperty("optionDetailId")
    private Long optionDetailId;
    @NotNull(groups = OnRegisterOrder.class)
    @JsonProperty("value")
    private String optionValue;


    public void updateOptionDataList(List<OptionDataDto> optionDataDtoList) {
        this.optionDataList=optionDataDtoList;
    }
}
