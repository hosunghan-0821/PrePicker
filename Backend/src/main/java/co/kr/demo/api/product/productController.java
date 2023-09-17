package co.kr.demo.api.product;


import co.kr.demo.service.dto.ResponseDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import co.kr.demo.service.product.Interface.IProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class productController {

    private final IProductFacade productFacade;



    @PostMapping("/product")
    public ResponseEntity<ResponseDto<ProductViewDto>> registerProduct(@RequestBody ProductViewDto productViewDto) {
        productFacade.productRegister(productViewDto);
        return ResponseEntity.ok(ResponseDto.response(productViewDto));
    }

    @GetMapping("/product")
    public ResponseEntity<ResponseDto<Page<ProductViewDto>>> getProductList(Pageable pageable) {
        final Page<ProductViewDto> productList = productFacade.getProductList(pageable);
        return ResponseEntity.ok(ResponseDto.response(productList));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseDto<ProductViewDto>> getProductDetail(@PathVariable Long id) {
        final ProductViewDto productDetail = productFacade.getProductDetail(id);
        return ResponseEntity.ok(ResponseDto.response(productDetail));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseEntity<ProductViewDto>> updateProductDetail(@PathVariable Long id){

        return null;
    }


}
