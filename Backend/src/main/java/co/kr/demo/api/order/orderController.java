package co.kr.demo.api.order;


import co.kr.demo.service.dto.ResponseDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.order.Interface.IOrderFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class orderController {

    private final IOrderFacade orderFacade;


    @PostMapping("/order")
    @ApiOperation(value = "주문 저장")
    public ResponseEntity<ResponseDto<OrderViewDto>> registerOrder(@RequestBody OrderViewDto orderViewDto) {

        //Validation 작업 진행 필요
        orderFacade.registerOrder(orderViewDto);
        return ResponseEntity.ok(ResponseDto.response(orderViewDto));
    }

    @PostMapping("/order/detail")
    @ApiOperation(value = "주문 상세보기")
    public ResponseEntity<ResponseDto<OrderViewDto>> getOrderDetail(@RequestBody OrderViewDto orderViewDto) {
        //조회 규칙 핸드폰 번호,성명 모두 같아야 조회
        OrderViewDto orderDetail = orderFacade.getOrderDetail(orderViewDto);
        return ResponseEntity.ok(ResponseDto.response(orderDetail));
    }

    /*
    *   To-DO
    *   주문취소 개발해야함
    * */
}
