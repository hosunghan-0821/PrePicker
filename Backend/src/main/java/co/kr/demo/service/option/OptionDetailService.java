package co.kr.demo.service.option;


import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.OptionDetail;
import co.kr.demo.domain.model.OrderProduct;
import co.kr.demo.repository.OptionDetailRepository;
import co.kr.demo.repository.OptionRepository;
import co.kr.demo.repository.OrderProductRepository;
import co.kr.demo.service.dto.domainDto.OptionDetailDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .additionalFee(optionDetailDto.getFee())
                .build();

        optionDetailRepository.save(optionDetail);
    }
}
