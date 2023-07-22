package co.kr.demo.repository;

import co.kr.demo.domain.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
