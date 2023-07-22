package co.kr.demo.api.order;


import co.kr.demo.service.order.dto.OrderDto;
import co.kr.demo.service.order.dto.OrderInfoDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class orderController {

    @PostMapping("/product")
    public void createProduct(@RequestBody OrderDto orderDto){

    }
}
