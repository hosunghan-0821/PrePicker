package co.kr.demo.repository;

import co.kr.demo.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
