package credit.conveer.msApplication.controller;

import credit.conveer.msApplication.dto.LoanApplicationRequestDto;
import credit.conveer.msApplication.dto.LoanOfferDto;
import credit.conveer.msApplication.service.DealRequestOffersService;
import credit.conveer.msApplication.service.DealRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Controller for validate application")
@RestController
@AllArgsConstructor
@RequestMapping("/application")
@Slf4j
public class MsApplicatonController {

    private final DealRequestService dealRequestService;
    private final DealRequestOffersService dealRequestOffersService;

    @ApiOperation(" prescoring and calculation Of Possible Conditions")
    @PostMapping()
    public List<LoanOfferDto> prescoringAndEvaluateRules(@RequestBody LoanApplicationRequestDto dto) {
        log.info("enter LoanApplicationRequestDto in MsApplicatonControlle   is ={} ", dto);
        return dealRequestService.prescoringAndEvaluateRules(dto);
    }

    @ApiOperation("selected one of Possible Conditions")
    @PutMapping("/offer")
    public void selectOneOfConditions(@RequestBody LoanOfferDto dto) {
        log.info("enter LoanOfferDto in MsApplicatonControlle   is ={} ", dto);
        dealRequestOffersService.selectOneOfOffers(dto);
    }
}
