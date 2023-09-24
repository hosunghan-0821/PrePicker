package co.kr.demo.service.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.repository.order.OrderRepository;
import co.kr.demo.service.dto.domainDto.OrderDto;
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

    public OrderDto getOrder(OrderDto orderDto) {

        final Order savedOrder = orderRepository.findOrderByClientNameAndClientPhoneNum(orderDto.getClientName(), orderDto.getClientPhoneNum())
                .orElseThrow(RuntimeException::new);
        return OrderDto.of(savedOrder);
    }
}
