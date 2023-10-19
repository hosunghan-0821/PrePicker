package com.example.demo.Efficiency;

;
import co.kr.demo.PrePickerApplication;
import co.kr.demo.domain.model.Product;
import co.kr.demo.repository.product.ProductRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = PrePickerApplication.class)
@ActiveProfiles("h2test")
public class BatchInsertTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Disabled
    @DisplayName("Batch Insert 성능 vs JPA SaveAll()")
    public void test() {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Product product = Product.builder()
                    .productName("test" + i)
                    .productCode("2123" + i)
                    .price(123L)
                    .MClassification("123")
                    .LClassification("456")
                    .build();
            productList.add(product);
        }
        //test1

        Instant start = Instant.now();

        productRepository.bulkInsertProducts(productList);


        // 실행 종료 시간 측정
        Instant end = Instant.now();


        long elapsedTime = java.time.Duration.between(start, end).toMillis();
        System.out.println("총 실행 시간: " + elapsedTime + " 밀리초");

        //test2
        start = Instant.now();

        productRepository.saveAll(productList);

        // 실행 종료 시간 측정
        end = Instant.now();

        // 실행 속도 계산 및 출력
        elapsedTime = java.time.Duration.between(start, end).toMillis();
        System.out.println("총 실행 시간: " + elapsedTime + " 밀리초");

    }
}
