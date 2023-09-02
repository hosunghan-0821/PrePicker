package co.kr.demo.repository.product;

import co.kr.demo.domain.model.Order;
import co.kr.demo.domain.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {
}
