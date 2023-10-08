package co.kr.demo.domain.model;


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

    private String LClassification;
    private String MClassification;
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
        this.LClassification=productDto.getLClassification();
        this.MClassification=productDto.getMClassification();
    }
}
