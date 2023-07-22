package co.kr.demo.service.option;

import co.kr.demo.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public void isExistOption(Long optionId){
        optionRepository.findById(optionId)
                .orElseThrow(()->new RuntimeException("exception 처리 해야함"));
    }

}
