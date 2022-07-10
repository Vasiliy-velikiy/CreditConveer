package credit.conveer.ms1.Service;


import credit.conveer.ms1.Dto.CreditDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;

public interface CreditCalculation {

    CreditDTO getCalculatedOffers(ScoringDataDTO scoringDataDTO);
}

