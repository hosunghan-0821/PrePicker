package co.kr.demo.service.order.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @JsonProperty("id")
    private Long orderId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientPhoneNum")
    private String clientPhoneNum;

    @JsonProperty("products")
    private List<ProductInfoDto> productsInfo;


}
