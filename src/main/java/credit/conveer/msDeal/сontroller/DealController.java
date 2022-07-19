package credit.conveer.msDeal.сontroller;

import credit.conveer.ms1.dto.LoanApplicationRequestDto;
import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.dto.FinishRegistrationRequestDto;
import credit.conveer.msDeal.service.CreateApplicationService;
import credit.conveer.msDeal.service.CreateClienService;
import credit.conveer.msDeal.service.DealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Controller for registration and evaluation credit ")
@RestController
@AllArgsConstructor
@RequestMapping("/deal")
@Slf4j
public class DealController {

    private final CreateClienService createClienService;
    private final DealService dealService;
    private final CreateApplicationService createApplicationService;

    @ApiOperation("calculation Of PossibleConditions")
    @PostMapping("/application")
    public List<LoanOfferDto> calculationOfPossibleConditions(@RequestBody LoanApplicationRequestDto dto) {
        log.info(" send appDto and return 4 LoanOfferDTO for client = {}",dto);
        return createClienService.createClientAndApplication(dto);
    }

    @ApiOperation("select One Of Offers")
    @PutMapping("/offer")
    public void selectOneOfOffers(@RequestBody LoanOfferDto dto) {
        log.info(" put LoanOfferDTO = {}", dto);
        dealService.selectOneOfOffers(dto);

    }

    @PutMapping("/calculate/{applicationId}")
    public void completionOfRegistrationAndFullCreditCalculation(@RequestBody FinishRegistrationRequestDto dto, @PathVariable Long applicationId) {
        log.info(" put FinishRegistrationRequestDTO = {} ,id = {}", dto,applicationId);
        createApplicationService.completionOfRegistrationAndFullCreditCalculation(dto, applicationId);
    }


}
