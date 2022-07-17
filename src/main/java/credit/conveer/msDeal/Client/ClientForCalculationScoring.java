package credit.conveer.msDeal.Client;


import credit.conveer.ms1.Dto.CreditDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "ClientForCalculationScoring", url ="${urlForCreditCalculation}")
public interface ClientForCalculationScoring {


    @PostMapping
    CreditDTO calculateScoringDTO(ScoringDataDTO dataDTO);
}
