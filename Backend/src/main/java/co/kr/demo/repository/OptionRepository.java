package co.kr.demo.repository;

import co.kr.demo.domain.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long> {
}
