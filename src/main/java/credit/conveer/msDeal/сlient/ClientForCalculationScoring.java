package credit.conveer.msDeal.сlient;


import credit.conveer.ms1.dto.CreditDto;
import credit.conveer.ms1.dto.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "ClientForCalculationScoring", url ="${urlForCreditCalculation}")
public interface ClientForCalculationScoring {

    @PostMapping
    CreditDto calculateScoringDTO(ScoringDataDto dataDTO);
}
