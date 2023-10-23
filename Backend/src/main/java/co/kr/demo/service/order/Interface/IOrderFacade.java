package co.kr.demo.service.order.Interface;

import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IOrderFacade {
    void registerOrder(OrderViewDto orderDto);

    OrderViewDto getOrderDetail(OrderViewDto orderDto);

    Page<OrderViewDto> getOrderList(Pageable pageable, SearchConditionDto searchConditionDto);

    Boolean cancelOrder(OrderViewDto orderViewDto);
}
