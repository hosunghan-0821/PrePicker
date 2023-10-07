package co.kr.demo.service.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.infra.sms.SMSMessageDto;
import co.kr.demo.infra.sms.SMSMessageType;
import co.kr.demo.infra.sms.SMSService;
import co.kr.demo.repository.order.OrderRepository;
import co.kr.demo.service.dto.domainDto.*;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.option.OptionDetailService;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import co.kr.demo.service.option.OptionService;
import co.kr.demo.service.order.Interface.IOrderFacade;
import co.kr.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderFacadeImpl implements IOrderFacade {

    private final OrderService orderService;

    private final ProductService productService;
    private final OrderProductService orderProductService;

    private final OptionDetailService optionDetailService;

    private final OptionService optionService;

    private final SMSService smsService;


    @Override
    public void registerOrder(OrderViewDto orderViewDto) {

        // 1. order 기본 정보 저장
        final OrderDto savedOrderDto = orderService.saveOrder(OrderDto.toOrderDtoByViewDto(orderViewDto));

        // 2,3 orderProduct 저장 및 optionDetails 저장
        for (ProductViewDto productViewDto : orderViewDto.getProducts()) {

            final ProductDto productDto = ProductDto.toProductDtoByViewDto(productViewDto);

            productService.isExistProduct(productViewDto.getProductId());

            final OrderProductDto orderProductDto = orderProductService.saveOrderProduct(savedOrderDto, productDto);

            for (OptionViewDto optionViewDto : productViewDto.getOptionDetails()) {

                optionService.isExistOption(optionViewDto.getOptionId());
                optionDetailService.saveOptionDetail(OptionDto.toOptionDtoByViewDto(optionViewDto), orderProductDto, OptionDetailDto.toOptionDetailDtoByViewDto(optionViewDto));
            }
        }
        final SMSMessageDto smsMessageDto = smsService.makeSMSMessage(orderViewDto, SMSMessageType.ORDER_CONFIRM);

        smsService.sendMessage(new ArrayList<>(Arrays.asList(smsMessageDto)));

    }

    @Override
    @Transactional(readOnly = true)
    public OrderViewDto getOrderDetail(OrderViewDto orderViewDto) {

        //기본주문정보
        final OrderDto orderDto = orderService.getOrder(OrderDto.toOrderDtoByViewDto(orderViewDto));

        //상품정보 + 옵션
        final List<OrderProductDto> orderProductDtoList = orderProductService.getOrderProduct(orderDto);

        List<ProductViewDto> productViewDtoList = new ArrayList<>();
        for (OrderProductDto orderProductDto : orderProductDtoList) {

            final List<OptionViewDto> optionDetail = optionDetailService.getOptionDetail(orderProductDto);
            final ProductViewDto productViewDto = ProductDto.productViewDtoByProductDtoAndOptionViewDto(orderProductDto.getProductDto(), optionDetail);
            productViewDtoList.add(productViewDto);
        }


        return OrderDto.toOrderViewDtoByData(orderDto, productViewDtoList);

    }
}
