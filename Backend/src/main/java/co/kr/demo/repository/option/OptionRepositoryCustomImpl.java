package co.kr.demo.repository.option;

import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.QOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static co.kr.demo.domain.model.QOption.option;


@RequiredArgsConstructor
@Repository
public class OptionRepositoryCustomImpl implements OptionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Option> findAllOptionList(Pageable pageable) {

        final List<Option> optionList = jpaQueryFactory.selectFrom(option)
                .where(option.isDeleted.isFalse())
                .orderBy(option.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final int size = jpaQueryFactory.select(option.id).from(option)
                .where(option.isDeleted.isFalse())
                .fetch().size();


        return new PageImpl<>(optionList,pageable,size);
    }
}
