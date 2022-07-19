package credit.conveer.msDeal.service;

import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.ApplicationStatusHistory;

public interface DealService {

    void selectOneOfOffers(LoanOfferDto dto);

    Application fetchApplFromRepo(LoanOfferDto dto);

    ApplicationStatusHistory createApplStatusHistory(Application application);
}
