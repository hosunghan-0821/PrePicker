package co.kr.demo.service.product;

import co.kr.demo.domain.model.Product;
import co.kr.demo.repository.ProductRepository;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void isExistProduct(Long productId) {

        final Product product = productRepository.findById(productId)
                .orElseThrow(()->(new RuntimeException("예외처리 필요")));


    }
}
