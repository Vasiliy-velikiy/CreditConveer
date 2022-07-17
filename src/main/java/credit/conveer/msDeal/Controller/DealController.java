package credit.conveer.msDeal.Controller;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;
import credit.conveer.msDeal.Service.CreateApplicationService;
import credit.conveer.msDeal.Service.CreateClienService;
import credit.conveer.msDeal.Service.DealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Controller for registration and evaluation credit ")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/msDeal")
@Slf4j
public class DealController {

    private final CreateClienService createClienService;
    private final DealService dealService;
    private final CreateApplicationService createApplicationService;

    @ApiOperation("calculation Of PossibleConditions")
    @PostMapping("/deal/application")
    public List<LoanOfferDTO> calculationOfPossibleConditions(@RequestBody LoanApplicationRequestDTO dto) {
        log.info(" send appDto and return 4 LoanOfferDTO for client");
        log.info(dto.toString());
        return createClienService.createClientAndApplication(dto);
    }

    @ApiOperation("select One Of Offers")
    @PutMapping("/deal/offer")
    public void selectOneOfOffers(@RequestBody LoanOfferDTO dto) {
        log.info(" put LoanOfferDTO");
        log.info(dto.toString());
        dealService.selectOneOfOffers(dto);

    }

    @PutMapping("/deal/calculate/{applicationId}")
    public void completionOfRegistrationAndFullCreditCalculation(@RequestBody FinishRegistrationRequestDTO dto, @PathVariable Long applicationId) {
        log.info(" put FinishRegistrationRequestDTO");
        log.info(" id= " + applicationId + " ,dto= " + dto.toString());
        createApplicationService.completionOfRegistrationAndFullCreditCalculation(dto, applicationId);
    }


}
