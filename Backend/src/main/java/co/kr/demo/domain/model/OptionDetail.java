package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EOptionType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_option_detail")
public class OptionDetail  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderProduct orderProduct;
    private String optionValue;
    private Long additionalFee;


}
