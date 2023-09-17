package co.kr.demo.domain.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
