package co.kr.demo.service.option;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.OptionData;
import co.kr.demo.domain.model.Product;
import co.kr.demo.domain.model.ProductOption;
import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.repository.option.OptionRepository;
import co.kr.demo.repository.product.ProductOptionRepository;
import co.kr.demo.service.dto.domainDto.OptionDataDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OptionDataService optionDataService;

    @Transactional
    public void saveOption(OptionDto optionDto) {

        Option option = OptionDto.toOption(optionDto);

        final Option savedOption = optionRepository.save(option);
        final OptionDto savedOptionDto = OptionDto.of(savedOption);

        savedOptionDto.updateOptionData(optionDto.getOptionData());
        optionDataService.savedOptionData(savedOptionDto);

    }


    @Transactional(readOnly = true)
    public OptionViewDto getOptionDetail(Long optionId) {
        final Option option = optionRepository.findById(optionId).orElseThrow(NotFoundException::new);
        OptionDto optionDto = OptionDto.of(option);
        return makeOptionViewDto(optionDto);
    }

    @Transactional(readOnly = true)
    public Page<OptionViewDto> getOptionList(Pageable pageable) {
        final Page<Option> allOptionList = optionRepository.findAllOptionList(pageable);
        final List<OptionViewDto> optionViewDtoList = new ArrayList<>();
        for (Option option : allOptionList.getContent()) {
            OptionDto optionDto = OptionDto.of(option);
            final OptionViewDto optionViewDto = makeOptionViewDto(optionDto);

            optionViewDtoList.add(optionViewDto);
        }
        return new PageImpl<>(optionViewDtoList, pageable, allOptionList.getSize());
    }

    private OptionViewDto makeOptionViewDto(OptionDto optionDto) {

        final OptionViewDto optionViewDto = OptionDto.toOptionViewDtoByOptionDto(optionDto);
        final List<OptionData> optionDataList = optionDataService.getOptionDataList(optionDto);

        final List<OptionDataDto> optionDataDtoList = optionDataList.stream().map(OptionDataDto::toOptionDataDto).collect(Collectors.toList());
        optionViewDto.updateOptionDataList(optionDataDtoList);

        return optionViewDto;
    }

    public void deleteOption(Long optionId) {
        final Option option = optionRepository.findById(optionId).orElseThrow(NotFoundException::new);
        option.softDelete();
    }

    public OptionDto isExistOption(Long optionId) {
        final Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION));
        return OptionDto.of(option);
    }


    public void saveProductOption(OptionDto optionDto, ProductDto productDto) {
        //존재하는 상품 옵션인지 확인
        isExistOption(optionDto.getOptionId());

        final Product savedProduct = ProductDto.toProduct(productDto);
        final ProductOption productOption = ProductOption.builder()
                .option(Option.builder().id(optionDto.getOptionId()).build())
                .product(savedProduct)
                .build();
        productOptionRepository.save(productOption);
    }

    public void updateProductOption(ProductDto productDto, List<OptionDto> optionDtoList) {
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
        if (allByProductAndOption.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION_PRODUCT_OPTION);
        }
    }


}
