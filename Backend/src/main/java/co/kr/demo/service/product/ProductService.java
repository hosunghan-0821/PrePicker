package co.kr.demo.service.product;

import co.kr.demo.domain.model.Product;
import co.kr.demo.global.error.dto.ErrorCode;
import co.kr.demo.global.error.exception.NotFoundException;
import co.kr.demo.repository.product.ProductRepository;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.util.CSVParser;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CSVParser csvParser;


    public ProductDto registerProduct(ProductDto productDto) {
        final Product savedProduct = productRepository.save(ProductDto.toProduct(productDto));
        return ProductDto.of(savedProduct);
    }

    public Product isExistProduct(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> (new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION_PRODUCT)));
    }


    public Page<ProductDto> getProductList(Pageable pageable) {
        return productRepository.getProductList(pageable);
    }

    public ProductDto getProductDetail(Long id) {
        return productRepository.findByIdWithFetch(id);
    }

    public void updateProduct(ProductDto productDto) {
        final Product existProduct = isExistProduct(productDto.getProductId());
        existProduct.updateProduct(productDto);

    }

    @Transactional
    public void registerProductWithCSV(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            List<Product> productList = new ArrayList<>();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            CSVReader csvReader = new CSVReader(new FileReader(file));
            csvReader.skip(1);

            csvReader.forEach(data -> {
                final Product parsedProduct = csvParser.parsingCSVToProduct(data);
                productList.add(parsedProduct);
            });
            csvReader.close();
            productRepository.bulkInsertProducts(productList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CSV Read FILE & Parsing ERROR");
        }

        //마지막에 파일삭제
        if (file.exists()) {
            if (file.delete()) {
                log.info("CSV Data Insert 및 Delete used File Success");
            } else {
                log.info("CSV Data Insert error: delete File Error ");
            }
        }

    }
}
