package co.kr.demo.service.dto.viewDto;


import co.kr.demo.domain.model.Order;
import co.kr.demo.service.dto.domainDto.OrderDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

import static co.kr.demo.global.error.dto.ErrorDetailMessage.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderViewDto {

    @JsonProperty("id")
    private Long orderId;

    @NotNull(message = NOT_NULL_CLIENT_NAME)
    @JsonProperty("clientName")
    private String clientName;

    @NotNull(message = NOT_NULL_CLIENT_PHONE_NUM)
    @JsonProperty("clientPhoneNum")
    private String clientPhoneNum;

    @NotNull(message=NOT_NULL_RESERVATION_DATE)
    @JsonProperty("reservationDate")
    private Instant reservationDate;

    @Valid
    @NotEmpty
    @JsonProperty("products")
    public List<ProductViewDto> products;

}
