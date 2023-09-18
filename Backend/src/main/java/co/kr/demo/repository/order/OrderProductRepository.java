package co.kr.demo.repository.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.domain.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    List<OrderProduct> findOrderProductsByOrder(Order order);
}
