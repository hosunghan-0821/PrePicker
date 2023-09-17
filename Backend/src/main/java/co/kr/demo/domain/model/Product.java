package co.kr.demo.domain.model;


import co.kr.demo.domain.model.enumeration.EMClassification;
import co.kr.demo.domain.model.enumeration.ESClassification;
import co.kr.demo.service.dto.domainDto.ProductDto;
import lombok.*;
import org.hibernate.annotations.Where;

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
    @Where(clause = "is_deleted=false")
    private List<ProductOption> productOptionList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Image> imageList;

    public void updateProduct(ProductDto productDto) {
        this.productName = productDto.getProductName();
        this.productCode = productDto.getProductCode();
        this.price = productDto.getProductPrice();
        // 추후에 eMClassification, eSClassification 추가 되어야함.
    }
}
