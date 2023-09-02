package co.kr.demo.domain.model;


import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "tb_order")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientName;
    private String clientPhoneNum;
    private Instant reservationDate;
    private Boolean isReceived;

}
