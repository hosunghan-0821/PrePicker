package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EMClassification;
import co.kr.demo.domain.model.enumeration.ESClassification;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    @Enumerated(EnumType.STRING)
    private EMClassification eMClassification;

    @Enumerated(EnumType.STRING)
    private ESClassification eSClassification;
    private String productCode;
    private Long price;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductOption> productOptionList;
}
