package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EOptionDataType;
import co.kr.demo.domain.model.enumeration.EOptionType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_option_data")
public class OptionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionDataName;
    private String optionData;

    @Enumerated(EnumType.STRING)
    private EOptionDataType eOptionDataType;

    @JoinColumn(name = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;

}
