package co.kr.demo.service.dto.domainDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {

    @JsonProperty(value="order")
    public OrderDto orderDto;

    @JsonProperty(value="products")
    public List<ProductDto> productDto;


}
