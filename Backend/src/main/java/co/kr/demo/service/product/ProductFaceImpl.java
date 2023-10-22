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
            optionService.saveOptionWithProduct(OptionDto.toOptionDtoByViewDto(optionViewDto), productDto);
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
    @Transactional
    public void updateProductDetail(ProductViewDto productViewDto) {
        //1. 상품 기본정보 변경
        final ProductDto productDto = ProductDto.toProductDtoByViewDto(productViewDto);
        productService.updateProduct(productDto);

        //2. 기존 Option 제거 후 새로 Insert
        // 기존에 존재하는 것과 존재하지 않는것 추가

        final List<OptionDto> optionDtoList = productViewDto.getOptionDetails().stream().map(OptionDto::toOptionDtoByViewDto).collect(Collectors.toList());
        optionService.updateOption(productDto,optionDtoList);


        //3. 기존 이미지 내용 지우고, 새로운 이미지 추가
        imageService.deleteImage(productDto);

        for (ImageViewDto imageViewDto : productViewDto.getImageViewDtoList()) {
            imageService.saveImage(ImageDto.toImageDtoByViewDto(imageViewDto), productDto);
        }

    }


}
