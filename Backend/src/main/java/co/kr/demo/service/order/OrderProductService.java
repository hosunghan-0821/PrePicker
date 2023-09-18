package co.kr.demo.service.order;


import co.kr.demo.domain.model.Order;
import co.kr.demo.domain.model.OrderProduct;
import co.kr.demo.domain.model.Product;
import co.kr.demo.repository.order.OrderProductRepository;
import co.kr.demo.service.dto.domainDto.OrderDto;
import co.kr.demo.service.dto.domainDto.OrderProductDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;


    public OrderProductDto saveOrderProduct(OrderDto orderDto, ProductDto productDto) {


        final Order order = OrderDto.toOrder(orderDto);

        final Product product = ProductDto.toProduct(productDto);
        //Order-Product 다대다 테이블에 저장하기전에, Validation

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .build();
        orderProductRepository.save(orderProduct);
        return OrderProductDto.of(orderProduct);

    }


    public List<OrderProductDto> getOrderProduct(OrderDto orderDto) {
        final List<OrderProduct> orderProductsByOrder = orderProductRepository.findOrderProductsByOrder(Order.builder().id(orderDto.getOrderId()).build());

        return orderProductsByOrder.stream().map(OrderProductDto::of).collect(Collectors.toList());


    }
}
