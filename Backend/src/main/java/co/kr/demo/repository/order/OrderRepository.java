package co.kr.demo.repository.order;

import co.kr.demo.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {

}
