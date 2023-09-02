package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EOptionType;
import lombok.*;
import org.graalvm.compiler.options.OptionType;
;


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
    private String additionalData;

}
