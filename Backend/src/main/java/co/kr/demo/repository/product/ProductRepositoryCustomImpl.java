package co.kr.demo.repository.product;

import co.kr.demo.domain.model.Product;
import co.kr.demo.domain.model.QProduct;
import co.kr.demo.repository.product.ProductRepositoryCustom;
import co.kr.demo.service.dto.domainDto.ProductDto;
import co.kr.demo.service.dto.viewDto.ProductViewDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static co.kr.demo.domain.model.QProduct.product;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProductDto> getProductList(Pageable pageable) {
        final List<Product> productList = jpaQueryFactory.selectFrom(product)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalCounts = jpaQueryFactory.selectFrom(product)
                .orderBy(product.id.desc()).fetch().size();

        List<ProductDto> productViewDtoList = productList.stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());
        return new PageImpl<>(productViewDtoList,pageable,totalCounts);
    }
}
