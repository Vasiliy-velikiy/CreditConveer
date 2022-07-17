package credit.conveer.msDeal.Service;

import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.Employment;

public interface CreateApplicationService {

    void completionOfRegistrationAndFullCreditCalculation(FinishRegistrationRequestDTO dto, Long applicationId);

    Employment createEmployment(FinishRegistrationRequestDTO dto);

    ScoringDataDTO createScoringDataDTO(Application application, FinishRegistrationRequestDTO dto);
}
