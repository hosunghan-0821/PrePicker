package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EMClassification;
import co.kr.demo.domain.model.enumeration.ESClassification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "tb_product")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private EMClassification eMClassification;
    private ESClassification eSClassification;
    private String productCode;
    private Long price;
}
