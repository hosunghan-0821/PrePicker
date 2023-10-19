package co.kr.demo.repository.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface OrderCustomRepository {

    Page<Order> getOrderList(Pageable pageable, SearchConditionDto searchConditionDto);
}
