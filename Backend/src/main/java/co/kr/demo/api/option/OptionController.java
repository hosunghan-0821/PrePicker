package co.kr.demo.api.option;


import co.kr.demo.service.dto.domainDto.OptionDto;
import co.kr.demo.service.dto.viewDto.OptionViewDto;
import co.kr.demo.service.option.OptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OptionController {

    private final OptionService optionService;
    @PostMapping("/options")
    @ApiOperation(value = "옵션 저장")
    public void registerOption(@RequestBody OptionViewDto optionViewDto){
        optionService.saveOption(OptionDto.toOptionDtoByViewDto(optionViewDto));
    }

    @GetMapping("/options")
    @ApiOperation(value = "옵션 목록 조회")
    public Page<OptionViewDto> getOptionList(Pageable pageable){
        return optionService.getOptionList(pageable);
    }
    @GetMapping("/options/{id}")
    @ApiOperation(value = "옵션 상세조회")
    public OptionViewDto getOptionDetail(@PathVariable("id")Long id){
        return optionService.getOptionDetail(id);
    }

    @DeleteMapping("/options/{id}")
    @ApiOperation(value = "옵션 제거")
    public void deleteOption(@PathVariable("id")Long id){
        optionService.deleteOption(id);
    }
}
