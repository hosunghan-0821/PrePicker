package co.kr.demo.service.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.repository.OrderRepository;
import co.kr.demo.service.dto.domainDto.OrderDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDto saveOrder(OrderDto orderDto) {

        final Order savedOrder = orderRepository.save(OrderDto.toOrder(orderDto));


        return  OrderDto.of(savedOrder);
    }
}
