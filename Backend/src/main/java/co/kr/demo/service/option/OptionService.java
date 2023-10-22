package co.kr.demo.service.option;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.Product;
import co.kr.demo.domain.model.ProductOption;
import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.repository.option.OptionDataRepository;
import co.kr.demo.repository.option.OptionRepository;
import co.kr.demo.repository.product.ProductOptionRepository;
import co.kr.demo.repository.product.ProductRepository;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductOptionRepository productOptionRepository;


    private final OptionDataService optionDataService;

    public void isExistOption(Long optionId) {
        optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION));
    }

    public void saveOption(OptionDto optionDto) {

        Option option= OptionDto.toOption(optionDto);

        final Option savedOption = optionRepository.save(option);
        final OptionDto savedOptionDto = OptionDto.of(savedOption);

        savedOptionDto.updateOptionData(optionDto.getOptionData());
        optionDataService.exchangeOptionData(savedOptionDto);

    }

    public void saveOptionWithProduct(OptionDto optionDto, ProductDto productDto) {
        //옵션을 가져와서, prodcutOption에 박아야함...
        final Option savedOption = optionRepository.save(OptionDto.toOption(optionDto));

        final Product savedProduct = ProductDto.toProduct(productDto);
        final ProductOption productOption = ProductOption.builder()
                .option(savedOption)
                .product(savedProduct)
                .build();
        productOptionRepository.save(productOption);
    }

    public void updateOption(ProductDto productDto, List<OptionDto> optionDtoList) {
        final Product savedProduct = Product.builder().id(productDto.getProductId()).build();
        final List<ProductOption> productOptionList = productOptionRepository.findAllByProduct(savedProduct);
        final Map<Long, ProductOption> productOptionMap = productOptionList.stream().collect(Collectors.toMap(
                productOption -> productOption.getOption().getId(),
                productOption -> productOption
        ));
        for (OptionDto optionDto : optionDtoList) {

            if (optionDto.getOptionId() == null || optionDto.getOptionId() == 0) {
                final Option newOption = optionRepository.save(OptionDto.toOption(optionDto));
                productOptionRepository.save(ProductOption.builder().product(savedProduct).option(newOption).build());
            } else {
                final ProductOption productOption = productOptionMap.get(optionDto.getOptionId());
                if (productOption != null) {
                    productOption.getOption().updateOption(optionDto);
                }
                productOptionMap.remove(optionDto.getOptionId());
            }
        }

        for (Map.Entry<Long, ProductOption> entry : productOptionMap.entrySet()) {
            entry.getValue().softDelete();
        }

    }

    public void matchProductAndOption(OptionDto optionDto, ProductDto productDto) {
        final List<ProductOption> allByProductAndOption = productOptionRepository.findAllByProductAndOption(Product.builder().id(productDto.getProductId()).build(), Option.builder().id(optionDto.getOptionId()).build());
        if(allByProductAndOption.isEmpty()){
            throw new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION_PRODUCT_OPTION);
        }
    }


}
