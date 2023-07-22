package co.kr.demo.service.order.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {

    @JsonProperty("id")
    private Long productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("code")
    private String productCode;

    @JsonProperty("price")
    private String productPrice;

    @JsonProperty("options")
    private List<OptionDetailDto> optionDetails;

}
