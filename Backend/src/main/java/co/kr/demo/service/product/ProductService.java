package co.kr.demo.service.product;

import co.kr.demo.domain.model.Product;
import co.kr.demo.global.exception.ErrorCode;
import co.kr.demo.global.exception.NotFoundException;
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

    public Product isExistProduct(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> (new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION_PRODUCT)));
    }


    public Page<ProductDto> getProductList(Pageable pageable) {
        return productRepository.getProductList(pageable);
    }

    public ProductDto getProductDetail(Long id) {
        return productRepository.findByIdWithFetch(id);
    }

    public void updateProduct(ProductDto productDto) {
        final Product existProduct = isExistProduct(productDto.getProductId());
        existProduct.updateProduct(productDto);

    }

}
