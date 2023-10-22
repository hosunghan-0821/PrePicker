package co.kr.demo.service.option;


import co.kr.demo.domain.model.OptionData;
import co.kr.demo.repository.option.OptionDataRepository;
import co.kr.demo.service.dto.domainDto.OptionDataADto;
import co.kr.demo.service.dto.domainDto.OptionDataDto;
import co.kr.demo.service.dto.domainDto.OptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionDataService {

    private final OptionDataRepository optionDataRepository;

    public void exchangeOptionData(OptionDto optionDto) {


        switch (optionDto.getEOptionType()) {
            case CHOICE:
                if(optionDto.getOptionData()!=null && !optionDto.getOptionData().isEmpty()){
                    for(int i = 0; i <optionDto.getOptionData().size();i++){
                        if(optionDto.getOptionData().get(i)instanceof OptionDataADto){
                            OptionDataADto optionDataADto = (OptionDataADto) optionDto.getOptionData().get(i);
                            System.out.println(optionDataADto.getOptionDataName());
                            System.out.println(optionDataADto.getAnotherInfo());
                        }
                    }
                }
//                OptionDataADto optionDataADto = (OptionDataADto) optionDto.getOptionData();
//                for(OptionDataDto optionDataDto : optionDto.getOptionData()){
//                    if(optionDataDto instanceof OptionDataADto){
//                        System.out.println(true);
//                    }
//                    System.out.println(optionDataDto.getOptionDataName());
//                    System.out.println(optionDataDto.getOptionData());
//                    System.out.println("=========");
//                }
                break;

            default:
                return;
        }


    }
}
