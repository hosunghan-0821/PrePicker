package co.kr.demo.service.dto.viewDto;


import co.kr.demo.domain.model.Order;
import co.kr.demo.service.dto.domainDto.OrderDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderViewDto {

    @JsonProperty("id")
    private Long orderId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientPhoneNum")
    private String clientPhoneNum;

    @JsonProperty("reservationDate")
    private Instant reservationDate;

    @JsonProperty("products")
    public List<ProductViewDto> products;


}
