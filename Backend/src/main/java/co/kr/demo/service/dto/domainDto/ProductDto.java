package co.kr.demo.service.dto.domainDto;


import co.kr.demo.domain.model.Product;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;


    private String productCode;


    private Long productPrice;

    private List<OptionDto> optionDtoList;

    private List<ImageDto> imageDtoList;


    public static Product toProductByViewDto(ProductViewDto productViewDto) {
        return Product.builder()
                .id(productViewDto.getProductId())
                .price(productViewDto.getProductPrice())
                .productName(productViewDto.getProductName())
                .productCode(productViewDto.getProductCode())
                .build();
    }

    public static ProductDto toProductDtoByViewDto(ProductViewDto productViewDto) {
        return ProductDto.builder()
                .productId(productViewDto.getProductId())
                .productPrice(productViewDto.getProductPrice())
                .productName(productViewDto.getProductName())
                .productCode(productViewDto.getProductCode())
                .build();
    }

    public static Product toProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getProductId())
                .price(productDto.getProductPrice())
                .productName(productDto.getProductName())
                .productCode(productDto.getProductCode())
                .build();
    }

    public static ProductDto of(Product product) {

        if(product.getImageList()==null){
            return ProductDto.builder()
                    .productId(product.getId())
                    .productPrice(product.getPrice())
                    .productName(product.getProductName())
                    .productCode(product.getProductCode())
                    .optionDtoList(new ArrayList<>())
                    .imageDtoList(new ArrayList<>())
                    .build();
        }

        return ProductDto.builder()
                .productId(product.getId())
                .productPrice(product.getPrice())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .optionDtoList(new ArrayList<>())
                .imageDtoList(product.getImageList().stream().map(ImageDto::toImageDtoByImage).collect(Collectors.toList()))
                .build();
    }


    public static ProductViewDto productViewDtoByProductDto(ProductDto productDto) {
        return ProductViewDto.builder()
                .productId(productDto.getProductId())
                .productPrice(productDto.getProductPrice())
                .productName(productDto.getProductName())
                .productCode(productDto.getProductCode())
                .optionDetails(productDto.getOptionDtoList().stream().map(OptionDto::toOptionViewDtoByOptionDto).collect(Collectors.toList()))
                .imageViewDtoList(productDto.getImageDtoList().stream().map(ImageDto::toImageViewDtoByImageDto).collect(Collectors.toList()))
                .build();
    }

    public static ProductViewDto productViewDtoByProductDtoAndOptionViewDto(ProductDto productDto,List<OptionViewDto> optionViewDtoList) {
        return ProductViewDto.builder()
                .productId(productDto.getProductId())
                .productPrice(productDto.getProductPrice())
                .productName(productDto.getProductName())
                .productCode(productDto.getProductCode())
                .optionDetails(optionViewDtoList)

                .build();
    }


}
