package co.kr.demo.service.dto.viewDto;

import co.kr.demo.domain.model.Product;
import co.kr.demo.service.dto.domainDto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.swing.text.html.ImageView;
import java.util.List;


@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewDto {

    @JsonProperty("id")
    private Long productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("code")
    private String productCode;

    @JsonProperty("price")
    private Long productPrice;

    @JsonProperty("options")
    private List<OptionViewDto> optionDetails;

    @JsonProperty("images")
    private List<ImageViewDto> imageViewDtoList;

}
