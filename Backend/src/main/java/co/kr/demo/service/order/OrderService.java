package co.kr.demo.service.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.repository.order.OrderRepository;
import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import co.kr.demo.service.dto.domainDto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDto saveOrder(OrderDto orderDto) {

        final Order savedOrder = orderRepository.save(OrderDto.toOrder(orderDto));


        return OrderDto.of(savedOrder);
    }

    public OrderDto getOrder(OrderDto orderDto) {

        final Order savedOrder = orderRepository.findOrderByClientNameAndClientPhoneNumAndId(orderDto.getClientName(), orderDto.getClientPhoneNum(),orderDto.getOrderId())
                .orElseThrow(RuntimeException::new);
        return OrderDto.of(savedOrder);
    }

    public Page<OrderDto> getOrderList(Pageable pageable, SearchConditionDto searchConditionDto) {

        final Page<Order> orderList = orderRepository.getOrderList(pageable,searchConditionDto);
        final List<OrderDto> orderDtoList = orderList.getContent().stream().map(OrderDto::of).collect(Collectors.toList());

        return new PageImpl<>(orderDtoList,pageable,orderList.getTotalElements());
    }

    public void savePrice(OrderDto savedOrderDto) {
        Order order = orderRepository.findById(savedOrderDto.getOrderId()).orElseThrow(NotFoundException::new);
        order.updatePrice(savedOrderDto.getPrice());
    }

    public OrderDto cancelOrder(OrderDto orderDto) {
        final Order savedOrder = orderRepository.findOrderByClientNameAndClientPhoneNumAndId(orderDto.getClientName(), orderDto.getClientPhoneNum(),orderDto.getOrderId())
                .orElseThrow(RuntimeException::new);
        savedOrder.softDelete();
        return OrderDto.of(savedOrder);
    }
}
