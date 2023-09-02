package co.kr.demo.service.option;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.Product;
import co.kr.demo.domain.model.ProductOption;
import co.kr.demo.repository.option.OptionRepository;
import co.kr.demo.repository.product.ProductOptionRepository;
import co.kr.demo.repository.product.ProductRepository;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public void isExistOption(Long optionId) {
        optionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("exception 처리 해야함"));
    }

    public void saveOption(OptionDto optionDto, ProductDto productDto) {
        final Option savedOption = optionRepository.save(OptionDto.toOption(optionDto));

        final Product savedProduct = productRepository.findById(productDto.getProductId()).orElse(null);
        final ProductOption productOption = ProductOption.builder()
                .option(savedOption)
                .product(savedProduct)
                .build();
        productOptionRepository.save(productOption);

    }
}
