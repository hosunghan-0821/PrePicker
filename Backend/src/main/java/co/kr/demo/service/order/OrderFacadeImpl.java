package co.kr.demo.service.order;

import co.kr.demo.infra.discord.DiscordBot;
import co.kr.demo.infra.discord.DiscordChannel;
import co.kr.demo.infra.discord.EDiscordChannel;
import co.kr.demo.infra.sms.SMSMessageDto;
import co.kr.demo.infra.sms.SMSMessageType;
import co.kr.demo.infra.sms.SMSService;
import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import co.kr.demo.service.dto.domainDto.*;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.option.OptionDetailService;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import co.kr.demo.service.option.OptionService;
import co.kr.demo.service.order.Interface.IOrderFacade;
import co.kr.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    private final DiscordChannel discordChannel;


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
                final OptionDto optionDto = OptionDto.toOptionDtoByViewDto(optionViewDto);

                optionService.isExistOption(optionViewDto.getOptionId());
                optionService.matchProductAndOption(optionDto,productDto);

                optionDetailService.saveOptionDetail(optionDto, orderProductDto, OptionDetailDto.toOptionDetailDtoByViewDto(optionViewDto));
            }
        }

        //4 주문 성공 안내 메세지 발송
        final SMSMessageDto smsMessageDto = smsService.makeSMSMessage(orderViewDto, SMSMessageType.ORDER_CONFIRM);
        smsService.sendMessage(new ArrayList<>(Arrays.asList(smsMessageDto)));

        //5 관리자에게 Discord 메세지로 주문안내
        discordChannel.sendMessageToChannel(EDiscordChannel.ORDER_NOTIFICATION_ROOM,smsMessageDto.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderViewDto getOrderDetail(OrderViewDto orderViewDto) {

        //기본주문정보
        final OrderDto orderDto = orderService.getOrder(OrderDto.toOrderDtoByViewDto(orderViewDto));
        //상품정보 + 옵션
        final List<ProductViewDto> productViewDtoList = getProductViewDto(orderDto);


        return OrderDto.toOrderViewDtoByData(orderDto, productViewDtoList);
    }

    @Override
    public Page<OrderViewDto> getOrderList(Pageable pageable, SearchConditionDto searchConditionDto) {

        final Page<OrderDto> orderDtoList = orderService.getOrderList(pageable,searchConditionDto);
        final List<OrderViewDto> orderViewDtoList =new ArrayList<>();
        for(OrderDto orderDto :orderDtoList.getContent()){
            final List<ProductViewDto> productViewDtoList = getProductViewDto(orderDto);
            final OrderViewDto orderViewDto = OrderDto.toOrderViewDtoByData(orderDto, productViewDtoList);
            orderViewDtoList.add(orderViewDto);
        }
        return new PageImpl<>(orderViewDtoList,pageable,orderDtoList.getTotalElements());
    }

    //상품정보 + 옵션
    private List<ProductViewDto>  getProductViewDto(OrderDto orderDto){

        final List<OrderProductDto> orderProductDtoList = orderProductService.getOrderProduct(orderDto);

        List<ProductViewDto> productViewDtoList = new ArrayList<>();
        for (OrderProductDto orderProductDto : orderProductDtoList) {

            final List<OptionViewDto> optionDetail = optionDetailService.getOptionDetail(orderProductDto);
            final ProductViewDto productViewDto = ProductDto.productViewDtoByProductDtoAndOptionViewDto(orderProductDto.getProductDto(), optionDetail);
            productViewDtoList.add(productViewDto);
        }
        return productViewDtoList;

    }
}
