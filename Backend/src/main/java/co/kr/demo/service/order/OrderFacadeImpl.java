package co.kr.demo.service.order;

import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.option.OptionDetailService;
import co.kr.demo.service.dto.domainDto.OrderDto;
import co.kr.demo.service.dto.domainDto.OrderProductDto;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import co.kr.demo.service.option.OptionService;
import co.kr.demo.service.order.Interface.IOrderFacade;
import co.kr.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFacadeImpl implements IOrderFacade {

    private final OrderService orderService;

    private final ProductService productService;
    private final OrderProductService orderProductService;

    private final OptionDetailService optionDetailService;

    private final OptionService optionService;

    @Override
    @Transactional
    public void registerOrder(OrderViewDto orderViewDto) {
        /*
         * 주문
         * 1. order 기본 정보 저장
         * 2. orderProduct(productId validation  & 정보 저장 )
         * 3. 해당하는 optiondetail 저장
         * */

        //1. order 기본 정보 저장
        final OrderDto savedOrderDto = orderService.saveOrder(OrderDto.toOrderDtoByViewDto(orderViewDto));

        //2,3 orderProduct 저장 및 optionDetails 저장
        for (ProductViewDto productViewDto : orderViewDto.getProducts()) {

            ProductDto productDto = ProductDto.toProductDtoByViewDto(productViewDto);

            productService.isExistProduct(productViewDto.getProductId());
            final OrderProductDto orderProductDto = orderProductService.saveOrderProduct(savedOrderDto, productDto);

            for (OptionViewDto optionViewDto : productViewDto.getOptionDetails()) {
                optionService.isExistOption(optionViewDto.getOptionId());

                /*
                * 해당 옵션 저장하는 코드 짜야함
                *
                * */

                //optionDetailService.saveOptionDetail(optionDto, orderProductDto, )

            }


        }


    }
}
