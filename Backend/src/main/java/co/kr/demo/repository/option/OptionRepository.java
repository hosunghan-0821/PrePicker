package co.kr.demo.repository.option;

import co.kr.demo.domain.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option,Long> {

}
