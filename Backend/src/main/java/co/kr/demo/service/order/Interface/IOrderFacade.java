package co.kr.demo.service.order.Interface;

import co.kr.demo.service.dto.domainDto.OrderDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;

import java.util.List;

public interface IOrderFacade {
    void registerOrder(OrderViewDto orderDto);

    OrderViewDto getOrderDetail(OrderViewDto orderDto);

    List<OrderViewDto> getOrderDetails(String date);
}
