package co.kr.demo.api.order;


import co.kr.demo.service.dto.ResponseDto;
import co.kr.demo.service.dto.viewDto.OrderViewDto;
import co.kr.demo.service.order.Interface.IOrderFacade;
import co.kr.demo.service.dto.domainDto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class orderController {

    private final IOrderFacade orderFacade;

    @PostMapping("/product")
    public ResponseEntity<ResponseDto<OrderViewDto>> createProduct(@RequestBody OrderViewDto orderViewDto){

        //Validation 작업 진행
        orderFacade.registerOrder(orderViewDto);
        return ResponseEntity.ok(ResponseDto.response(orderViewDto));
    }
}
