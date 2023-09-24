package co.kr.demo.service.product.Interface;

import co.kr.demo.service.dto.viewDto.ProductViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductFacade {

    void productRegister(ProductViewDto productViewDto);

    Page<ProductViewDto> getProductList(Pageable pageable);

    ProductViewDto getProductDetail(Long id);

    void updateProductDetail(ProductViewDto productViewDto);
}
