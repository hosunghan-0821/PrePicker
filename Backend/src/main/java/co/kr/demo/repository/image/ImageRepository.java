package co.kr.demo.repository.image;

import co.kr.demo.domain.model.Image;
import co.kr.demo.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ImageRepository  extends JpaRepository<Image,Long> {


    List<Image> findAllByProduct(Product product);
}
