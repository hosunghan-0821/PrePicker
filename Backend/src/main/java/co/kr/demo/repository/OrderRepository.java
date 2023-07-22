package co.kr.demo.repository;

import co.kr.demo.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {

}
