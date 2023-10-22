package co.kr.demo.api.option;


import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OptionController {

    private final OptionService optionService;
    @PostMapping("/options")
    public void registerOption(@RequestBody OptionViewDto optionViewDto){

        optionService.saveOption(OptionDto.toOptionDtoByViewDto(optionViewDto));
    }
}
