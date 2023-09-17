package co.kr.demo.service.image;


import co.kr.demo.domain.model.Image;
import co.kr.demo.domain.model.Product;
import co.kr.demo.repository.image.ImageRepository;
import co.kr.demo.service.dto.domainDto.ImageDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    public void saveImage(ImageDto imageDto, ProductDto productDto) {
        final Image image = ImageDto.toImage(imageDto,productDto);

        imageRepository.save(image);
    }
}
