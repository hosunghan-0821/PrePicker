package co.kr.demo.service.dto.domainDto;


import co.kr.demo.domain.model.Order;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

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

    @JsonProperty("reservationDate")
    private Instant reservationDate;


    public static Order toOrderByViewDto(OrderViewDto orderViewDto){
        return Order.builder()
                .id(orderViewDto.getOrderId())
                .clientName(orderViewDto.getClientName())
                .clientPhoneNum(orderViewDto.getClientPhoneNum())
                .reservationDate(orderViewDto.getReservationDate())
                .build();
    }
    public static OrderDto toOrderDtoByViewDto(OrderViewDto orderViewDto) {
        return OrderDto.builder()
                .orderId(orderViewDto.getOrderId())
                .clientName(orderViewDto.getClientName())
                .clientPhoneNum(orderViewDto.getClientPhoneNum())
                .reservationDate(orderViewDto.getReservationDate())
                .build();
    }

    public static Order toOrder(OrderDto orderDto){
        return Order.builder()
                .id(orderDto.getOrderId())
                .clientName(orderDto.getClientName())
                .clientPhoneNum(orderDto.getClientPhoneNum())
                .reservationDate(orderDto.getReservationDate())
                .build();
    }


    public static OrderDto of(Order savedOrder) {
        return OrderDto.builder()
                .orderId(savedOrder.getId())
                .clientPhoneNum(savedOrder.getClientPhoneNum())
                .reservationDate(savedOrder.getReservationDate())
                .clientName(savedOrder.getClientName())
                .build();
    }


}
