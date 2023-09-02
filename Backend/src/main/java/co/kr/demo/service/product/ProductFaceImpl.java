package co.kr.demo.service.product;


import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import co.kr.demo.service.option.OptionService;
import co.kr.demo.service.product.Interface.IProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFaceImpl implements IProductFacade {

    private final ProductService productService;

    private final OptionService optionService;

    @Override
    public void productRegister(ProductViewDto productViewDto) {

        //1. 상품등록
        final ProductDto productDto = productService.registerProduct(ProductDto.toProductDtoByViewDto(productViewDto));

        //2. 상품 관련 Option 등록
        for (OptionViewDto optionViewDto : productViewDto.getOptionDetails()) {
            optionService.saveOption(OptionDto.toOptionDtoByViewDto(optionViewDto), productDto);
        }
    }

    @Override
    public Page<ProductViewDto> getProductList(Pageable pageable) {

        final Page<ProductDto> productList = productService.getProductList(pageable);
        final List<ProductViewDto> productViewDtoList = productList.stream().map(ProductDto::productViewDtoByProduct).collect(Collectors.toList());
        return new PageImpl<>(productViewDtoList,pageable,productList.getTotalElements());

    }


}
