package co.kr.demo.service.dto.viewDto;

import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageViewDto {

    private Long id;
    private String url;
    private Integer order;

}
