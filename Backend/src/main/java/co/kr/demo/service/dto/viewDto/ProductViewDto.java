package co.kr.demo.service.dto.viewDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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

    @JsonProperty("LClassification")
    private String LClassification;

    @JsonProperty("MClassification")
    private String MClassification;


    @JsonProperty("options")
    private List<OptionViewDto> optionDetails;

    @JsonProperty("images")
    private List<ImageViewDto> imageViewDtoList;

}
