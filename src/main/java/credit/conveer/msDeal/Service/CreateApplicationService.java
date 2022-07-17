package credit.conveer.msDeal.Service;

import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;

public interface CreateApplicationService {

    void completionOfRegistrationAndFullCreditCalculation (FinishRegistrationRequestDTO dto, Long applicationId);
}
