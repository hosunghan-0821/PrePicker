package co.kr.demo.repository.option;

import co.kr.demo.domain.model.OptionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionDataRepository extends JpaRepository<OptionData,Long> {
}
