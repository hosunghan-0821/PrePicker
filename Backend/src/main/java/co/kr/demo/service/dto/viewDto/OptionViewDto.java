package co.kr.demo.service.dto.viewDto;


import co.kr.demo.domain.model.enumeration.EOptionType;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnCreateProduct;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnRegisterOrder;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnUpdateProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionViewDto {


    //Domain :  Option 항목
    @NotNull(groups = OnUpdateProduct.class)
    @NotNull(groups = OnRegisterOrder.class)
    @JsonProperty("optionId")
    private Long optionId;

    @NotNull(groups = OnCreateProduct.class)
    @NotNull(groups = OnUpdateProduct.class)
    @JsonProperty("name")
    private String optionName;

    @NotNull(groups = OnCreateProduct.class)
    @NotNull(groups = OnUpdateProduct.class)
    @NotNull(groups = OnRegisterOrder.class)
    @JsonProperty("type")
    private EOptionType eOptionType;

    @JsonProperty("additionalData")
    private String additionalData;

    @NotNull(groups = OnCreateProduct.class)
    @NotNull(groups = OnUpdateProduct.class)
    @JsonProperty("fee")
    private Long fee;

    //Domain : OptionDetail 항목
    @JsonProperty("optionDetailId")
    private Long optionDetailId;
    @NotNull(groups = OnRegisterOrder.class)
    @JsonProperty("value")
    private String optionValue;


}
