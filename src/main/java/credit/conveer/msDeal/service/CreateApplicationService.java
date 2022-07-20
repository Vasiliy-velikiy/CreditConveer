package credit.conveer.msDeal.service;

import credit.conveer.ms1.dto.ScoringDataDto;
import credit.conveer.msDeal.dto.FinishRegistrationRequestDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.Employment;

public interface CreateApplicationService {

    void completionOfRegistrationAndFullCreditCalculation(FinishRegistrationRequestDto dto, Long applicationId);

    Employment createEmployment(FinishRegistrationRequestDto dto);

    ScoringDataDto createScoringDataDto(Application application, FinishRegistrationRequestDto dto);
}
