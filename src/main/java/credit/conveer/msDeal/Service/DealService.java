package credit.conveer.msDeal.Service;

import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.ApplicationStatusHistory;

public interface DealService {

    void selectOneOfOffers(LoanOfferDTO dto);

    Application fetchApplFromRepo(LoanOfferDTO dto);

    ApplicationStatusHistory createApplStatusHistory(Application application);
}
