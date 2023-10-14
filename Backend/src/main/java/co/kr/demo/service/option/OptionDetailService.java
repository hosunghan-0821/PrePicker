package co.kr.demo.service.option;


import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.OptionDetail;
import co.kr.demo.domain.model.OrderProduct;
import co.kr.demo.domain.model.ProductOption;
import co.kr.demo.repository.option.OptionDetailRepository;
import co.kr.demo.repository.option.OptionRepository;
import co.kr.demo.repository.order.OrderProductRepository;
import co.kr.demo.service.dto.domainDto.OptionDetailDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.OrderProductDto;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionDetailService {

    private final OptionDetailRepository optionDetailRepository;

    private final OrderProductRepository orderProductRepository;
    private final OptionRepository optionRepository;

    public void saveOptionDetail(OptionDto optionDto, OrderProductDto orderProductDto, OptionDetailDto optionDetailDto) {

        final Option option = optionRepository.findById(optionDto.getOptionId()).orElse(null);
        final OrderProduct orderProduct = orderProductRepository.findById(orderProductDto.getId()).orElse(null);

        final OptionDetail optionDetail = OptionDetail.builder()
                .option(option)
                .orderProduct(orderProduct)
                .optionValue(optionDetailDto.getOptionValue())
                .build();

        optionDetailRepository.save(optionDetail);
    }

    public List<OptionViewDto> getOptionDetail(OrderProductDto orderProductDto) {

        //JPQL FETCH JOIN 사용으로 수정
        final List<OptionDetail> optionDetailList = optionDetailRepository.findOptionDetailsByOrOrderProduct(OrderProduct.builder().id(orderProductDto.getId()).build());

        final List<OptionViewDto> optionViewDtoList=new ArrayList<>();
        for(OptionDetail optionDetail: optionDetailList){
            final OptionDto optionDto = OptionDto.of(optionDetail.getOption());
            final OptionDetailDto optionDetailDto = OptionDetailDto.of(optionDetail);
            final OptionViewDto optionViewDto = OptionDto.toOptionViewDtoByOptionDtoAndOptionDetailDto(optionDto, optionDetailDto);
            optionViewDtoList.add(optionViewDto);
        }
        return optionViewDtoList;
    }
}
