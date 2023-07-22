package co.kr.demo.service.option;


import co.kr.demo.repository.OptionDetailRepository;
import co.kr.demo.service.dto.domainDto.OptionDetailDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.domainDto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionDetailService {
    
    private final OptionDetailRepository optionDetailRepository;

    public void saveOptionDetail(OptionDto optionDto, OrderProductDto orderProductDto, OptionDetailDto optionDetailDto) {

    }
}
