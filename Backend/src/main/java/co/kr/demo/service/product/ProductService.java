package co.kr.demo.service.product;

import co.kr.demo.domain.model.Product;
import co.kr.demo.repository.product.ProductRepository;
import co.kr.demo.service.dto.domainDto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public ProductDto registerProduct(ProductDto productDto) {
        final Product savedProduct = productRepository.save(ProductDto.toProduct(productDto));
        return ProductDto.of(savedProduct);
    }

    public void isExistProduct(Long productId) {

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> (new RuntimeException("예외처리 필요")));


    }


    public Page<ProductDto> getProductList(Pageable pageable) {
        return productRepository.getProductList(pageable);
    }

    public ProductDto getProductDetail(Long id) {
        return productRepository.findByIdWithFetch(id);
    }
}
