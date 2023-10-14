package co.kr.demo.repository.product;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.Order;
import co.kr.demo.domain.model.Product;
import co.kr.demo.domain.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {

    List<ProductOption> findAllByProduct(Product product);

    List<ProductOption> findAllByProductAndOption(Product product, Option option);
}
