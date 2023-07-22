package co.kr.demo.service.dto.domainDto;


import co.kr.demo.domain.model.Product;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @JsonProperty("id")
    private Long productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("code")
    private String productCode;

    @JsonProperty("price")
    private Long productPrice;


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

    public static ProductDto of(Product product){
        return ProductDto.builder()
                .productId(product.getId())
                .productPrice(product.getPrice())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .build();
    }
}
