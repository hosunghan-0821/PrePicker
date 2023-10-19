package co.kr.demo.repository.order;

import co.kr.demo.domain.model.Order;
import co.kr.demo.domain.model.QOrder;
import co.kr.demo.service.dto.businessDto.SearchConditionDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;

import static co.kr.demo.domain.model.QOrder.order;

@RequiredArgsConstructor
@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Order> getOrderList(Pageable pageable, SearchConditionDto searchConditionDto) {

        final List<Order> orderList = jpaQueryFactory.selectFrom(order)
                .where(order.isDeleted.eq(false))
                .where(getSearchCondition(searchConditionDto))
                .orderBy(order.reservationDate.asc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        int totalElements = jpaQueryFactory.selectFrom(order)
                .where(order.isDeleted.eq(false))
                .where(getSearchCondition(searchConditionDto))
                .fetch().size();

        return new PageImpl<>(orderList, pageable, totalElements);
    }

    private BooleanExpression getSearchCondition(SearchConditionDto searchConditionDto) {

        if (searchConditionDto.getStartDate() != null && searchConditionDto.getEndDate() != null) {
            return order.reservationDate.loe(searchConditionDto.getEndDate())
                    .and(order.reservationDate.goe(searchConditionDto.getStartDate()));
        }
        return null;
    }
}
