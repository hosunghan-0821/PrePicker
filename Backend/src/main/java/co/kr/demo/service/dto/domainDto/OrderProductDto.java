package co.kr.demo.service.dto.domainDto;

import co.kr.demo.domain.model.OrderProduct;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {

    private Long id;
    private OrderDto orderDto;
    private ProductDto productDto;

    public static OrderProductDto of(OrderProduct orderProduct) {
        return OrderProductDto.builder()
                .id(orderProduct.getId())
                .orderDto(OrderDto.of(orderProduct.getOrder()))
                .productDto(ProductDto.of(orderProduct.getProduct()))
                .build();
    }
}
