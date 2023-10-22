package co.kr.demo.service.option;


import co.kr.demo.domain.model.Option;
import co.kr.demo.domain.model.OptionData;
import co.kr.demo.repository.option.OptionDataRepository;
import co.kr.demo.service.dto.domainDto.OptionDataDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionDataService {

    private final OptionDataRepository optionDataRepository;

    public void savedOptionData(OptionDto optionDto) {

        final Option savedOption = Option.builder().id(optionDto.getOptionId()).build();
        final List<OptionData> optionDataList = new ArrayList<>();
        if (optionDto.getOptionData() != null && !optionDto.getOptionData().isEmpty()) {
            switch (optionDto.getEOptionType()) {
                case CHOICE:
                    for (int i = 0; i < optionDto.getOptionData().size(); i++) {
                        final OptionData optionData = OptionDataDto.toOptionData(savedOption, optionDto.getOptionData().get(i));
                        optionDataList.add(optionData);
                    }
                    optionDataRepository.saveAll(optionDataList);
                    break;
                default:
                    return;
            }
        }


    }
}
