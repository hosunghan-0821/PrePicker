package co.kr.demo.domain.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "tb_product_option")
public class ProductOption extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;

}
