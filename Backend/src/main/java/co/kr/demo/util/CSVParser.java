package co.kr.demo.util;

import co.kr.demo.domain.model.Product;
import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.exception.InternalServerException;

import org.springframework.stereotype.Component;

@Component
public class CSVParser {

    public Product parsingCSVToProduct(String[] data) {

        try {
            return Product.builder()
                    .productCode(data[0])
                    .LClassification(data[1])
                    .MClassification(data[2])
                    .productName(data[3])
                    .price(Long.parseLong(data[4]))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_PARSING_ERROR);
        }
    }
}
