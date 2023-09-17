package co.kr.demo.repository.product;

import co.kr.demo.domain.model.Order;
import co.kr.demo.domain.model.Product;
import co.kr.demo.domain.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {

    List<ProductOption> findProductOptionByProductAndDeleted(Product product,boolean isDeleted);
}
