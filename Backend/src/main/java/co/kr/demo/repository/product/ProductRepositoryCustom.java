package co.kr.demo.repository.product;

import co.kr.demo.domain.model.Product;
import co.kr.demo.service.dto.domainDto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    Page<ProductDto> getProductList(Pageable pageable);

    ProductDto findByIdWithFetch(Long id);

    void bulkInsertProducts(List<Product> productList);
}
