package co.kr.demo.repository.option;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.OptionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionDataRepository extends JpaRepository<OptionData,Long> {

    List<OptionData> findOptionDataByOptionOrderByIdDesc(Option option);
}
