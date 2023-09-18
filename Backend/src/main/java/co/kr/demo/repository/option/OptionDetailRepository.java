package co.kr.demo.repository.option;

import co.kr.demo.domain.model.OptionDetail;
import co.kr.demo.domain.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionDetailRepository extends JpaRepository<OptionDetail,Long> {

    List<OptionDetail> findOptionDetailsByOrOrderProduct(OrderProduct orderProduct);

}
