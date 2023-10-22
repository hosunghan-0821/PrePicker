package co.kr.demo.repository.option;

import co.kr.demo.domain.model.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OptionRepositoryCustom {
    Page<Option> findAllOptionList(Pageable pageable);
}
