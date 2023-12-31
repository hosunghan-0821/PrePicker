package co.kr.demo.service.dto.viewDto;

import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnRegisterOrder;
import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnUpdateProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static co.kr.demo.global.error.dto.ErrorDetailMessage.*;


@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewDto {

    @JsonProperty("id")
    @NotNull(groups = {OnRegisterOrder.class})
    @NotNull(groups = {OnUpdateProduct.class})
    private Long productId;

    @JsonProperty("name")
    @NotNull(message = NOT_NULL_PRODUCT_NAME)
    private String productName;
    @NotNull(groups = {OnUpdateProduct.class}, message = NOT_NULL_PRODUCT_CODE)
    @JsonProperty("code")
    private String productCode;

    @NotNull(message = NOT_NULL_PRODUCT_PRICE)
    @JsonProperty("price")
    private Long productPrice;

    @JsonProperty("LClassification")
    private String LClassification;

    @JsonProperty("MClassification")
    private String MClassification;


    @Valid
    @JsonProperty("options")
    private List<OptionViewDto> optionDetails;

    @Valid
    @JsonProperty("images")
    private List<ImageViewDto> imageViewDtoList;

}
