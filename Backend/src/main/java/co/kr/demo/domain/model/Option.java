package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EOptionType;
import co.kr.demo.service.dto.domainDto.OptionDto;
import lombok.*;


import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_option")
public class Option extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionName;

    @Enumerated(EnumType.STRING)
    private EOptionType eOptionType;

    private Long additionalFee;

    public void updateOption(OptionDto optionDto) {
        this.optionName=optionDto.getOptionName();
        this.eOptionType=optionDto.getEOptionType();
        this.additionalFee=optionDto.getFee();

    }
}
