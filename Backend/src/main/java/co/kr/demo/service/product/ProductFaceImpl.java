package co.kr.demo.service.product;


import co.kr.demo.service.dto.domainDto.ImageDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.service.dto.viewDto.ImageViewDto;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import co.kr.demo.service.image.ImageService;
import co.kr.demo.service.option.OptionService;
import co.kr.demo.service.product.Interface.IProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductFaceImpl implements IProductFacade {

    private final ProductService productService;

    private final OptionService optionService;

    private final ImageService imageService;

    @Override
    public void productRegister(ProductViewDto productViewDto) {

        //1. 상품등록
        final ProductDto productDto = productService.registerProduct(ProductDto.toProductDtoByViewDto(productViewDto));

        //2. 상품 관련 Option 등록
        for (OptionViewDto optionViewDto : productViewDto.getOptionDetails()) {
            optionService.saveOption(OptionDto.toOptionDtoByViewDto(optionViewDto), productDto);
        }
        //3. 상품 관련 이미지 등록
        for (ImageViewDto imageViewDto : productViewDto.getImageViewDtoList()) {
            imageService.saveImage(ImageDto.toImageDtoByViewDto(imageViewDto), productDto);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductViewDto> getProductList(Pageable pageable) {

        final Page<ProductDto> productList = productService.getProductList(pageable);
        final List<ProductViewDto> productViewDtoList = productList.stream().map(ProductDto::productViewDtoByProductDto).collect(Collectors.toList());
        return new PageImpl<>(productViewDtoList, pageable, productList.getTotalElements());

    }

    @Override
    @Transactional(readOnly = true)
    public ProductViewDto getProductDetail(Long id) {
        final ProductDto productDetail = productService.getProductDetail(id);
        return ProductDto.productViewDtoByProductDto(productDetail);
    }

    @Override
    public ProductViewDto updateProductDetail(ProductViewDto productViewDto) {
        //1. 상품 기본정보 변경
        final ProductDto productDto = ProductDto.toProductDtoByViewDto(productViewDto);
        productService.updateProduct(productDto);

        //2. 기존 Option 내용 업데이트
        final List<OptionDto> optionDtoList = productViewDto.getOptionDetails().stream().map(OptionDto::toOptionDtoByViewDto).collect(Collectors.toList());
        for (OptionDto optionDto : optionDtoList) {
            optionService.updateOption(optionDto);
        }


        //3. 기존 이미지 내용 지우고, 새로운 이미지 추가

        return null;
    }


}
