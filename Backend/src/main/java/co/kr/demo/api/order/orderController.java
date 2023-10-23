package co.kr.demo.api.order;


import co.kr.demo.global.error.validation.ValidationMarkerInterfaceGroups.OnRegisterOrder;
import co.kr.demo.service.dto.ResponseDto;
import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.order.Interface.IOrderFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class orderController {

    private final IOrderFacade orderFacade;


    @PostMapping("/orders")
    @ApiOperation(value = "주문 저장")
    public ResponseEntity<ResponseDto<OrderViewDto>> registerOrder(@Validated(value = {OnRegisterOrder.class}) @RequestBody OrderViewDto orderViewDto) {

        //Validation 작업 진행 필요
        orderFacade.registerOrder(orderViewDto);
        return ResponseEntity.ok(ResponseDto.response(orderViewDto));
    }

    @PostMapping("/orders/detail")
    @ApiOperation(value = "주문 상세보기")
    public ResponseEntity<ResponseDto<OrderViewDto>> getOrderDetail(@RequestBody OrderViewDto orderViewDto) {
        //조회 규칙 핸드폰 번호,성명 모두 같아야 조회
        OrderViewDto orderDetail = orderFacade.getOrderDetail(orderViewDto);
        return ResponseEntity.ok(ResponseDto.response(orderDetail));
    }

    @GetMapping("/orders")
    @ApiOperation(value = "주문 다중조회")
    public ResponseEntity<ResponseDto<Page<OrderViewDto>>> getOrderList(Pageable pageable,
                                                                        @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                                                        @RequestParam(value = "endDate", defaultValue = "") String endDate) {


        final SearchConditionDto searchConditionDto = SearchConditionDto.builder()
                .startDate(SearchConditionDto.toParseInstant(startDate, SearchConditionDto.START_DATE))
                .endDate(SearchConditionDto.toParseInstant(endDate, SearchConditionDto.END_DATE))
                .build();
        final Page<OrderViewDto> orderList = orderFacade.getOrderList(pageable, searchConditionDto);
        return ResponseEntity.ok(ResponseDto.response(orderList));

    }

    @PostMapping("/orders/cancel")
    @ApiOperation(value = "주문 취소")
    public ResponseEntity<Boolean> cancelOrder(@RequestBody OrderViewDto orderViewDto) {

        //Validation 작업 진행 필요
        final Boolean result = orderFacade.cancelOrder(orderViewDto);
        return ResponseEntity.ok(result);
    }

}
