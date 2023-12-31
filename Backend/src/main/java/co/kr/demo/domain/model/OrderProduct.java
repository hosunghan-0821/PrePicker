package co.kr.demo.domain.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_order_product")
public class OrderProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    @OneToMany(mappedBy = "orderProduct")
    private List<OptionDetail> optionDetails;

}
