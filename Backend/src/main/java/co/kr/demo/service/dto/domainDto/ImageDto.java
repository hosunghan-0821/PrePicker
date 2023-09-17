package co.kr.demo.service.dto.domainDto;

import co.kr.demo.domain.model.Image;
import co.kr.demo.domain.model.Product;
import co.kr.demo.service.dto.viewDto.ImageViewDto;
import lombok.*;


@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {


    private Long id;
    private String url;
    private Integer order;

    public static ImageDto toImageDtoByViewDto(ImageViewDto imageViewDto) {
        return ImageDto.builder()
                .id(imageViewDto.getId())
                .order(imageViewDto.getOrder())
                .url(imageViewDto.getUrl())
                .build();
    }

    public static Image toImage(ImageDto imageDto,ProductDto productDto) {
        final Product product = ProductDto.toProduct(productDto);
        return Image.builder()
                .order(imageDto.getOrder())
                .url(imageDto.getUrl())
                .id(imageDto.getId())
                .product(product)
                .build();
    }

    public static ImageViewDto toImageViewDtoByImageDto(ImageDto imageDto) {
        return ImageViewDto.builder()
                .order(imageDto.getOrder())
                .url(imageDto.getUrl())
                .id(imageDto.getId())
                .build();
    }

    public static ImageDto toImageDtoByImage(Image image) {
       return ImageDto.builder()
                .id(image.getId())
                .order(image.getOrder())
                .url(image.getUrl())
                .build();
    }
}
