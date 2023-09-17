package co.kr.demo.repository.image;

import co.kr.demo.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ImageRepository  extends JpaRepository<Image,Long> {
}
