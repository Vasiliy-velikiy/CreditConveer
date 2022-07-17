package credit.conveer.ms1.Controller;

import credit.conveer.ms1.Dto.CreditDTO;
import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.ms1.Service.CreditCalculation;
import credit.conveer.ms1.Service.PrescoringOffers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api("Controller for accepting and processing credit ")
@RestController
@RequestMapping( path ="/creditConveyor")
@Slf4j
public class Conveyor {

    private final PrescoringOffers prescoring;

    private final CreditCalculation creditCalculation;

    public Conveyor(PrescoringOffers prescoring, CreditCalculation creditCalculation) {
        this.prescoring = prescoring;
        this.creditCalculation = creditCalculation;
    }

    @ApiOperation("submitting a loan application and obtaining a calculation of possible loan conditions ")
    @PostMapping("/offers") //приходит заявка на получение кредита
    public List<LoanOfferDTO> offers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("4 LoanOfferDTO for client");
        return prescoring.getPrescoringOffers(loanApplicationRequestDTO);
    }

    @ApiOperation("validation of submitted data + data scoring + full calculation of loan parameters")
    @PostMapping("/calculation")
    public CreditDTO calculation(@RequestBody ScoringDataDTO scoringDataDTO) {
        log.info("all evaluated CreditDTO");
        return creditCalculation.getCalculatedOffers(scoringDataDTO);
    }

}