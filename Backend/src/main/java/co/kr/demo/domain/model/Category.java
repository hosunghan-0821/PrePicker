package co.kr.demo.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Entity
//@Getter
//@Setter(AccessLevel.PROTECTED)
//@NoArgsConstructor
////일단 보류.. 따로 테이블 뺄 필요가 있는지 의문
//@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
