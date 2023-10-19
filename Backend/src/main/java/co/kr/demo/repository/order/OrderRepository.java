package co.kr.demo.repository.order;

import co.kr.demo.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> , OrderCustomRepository  {

    Optional<Order> findOrderByClientNameAndClientPhoneNum(String clientName,String clientPhoneNum);
}
