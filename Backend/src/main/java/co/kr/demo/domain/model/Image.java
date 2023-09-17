package co.kr.demo.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column(name = "image_order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;
}
