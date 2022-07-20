package credit.conveer.msDeal.service;

import credit.conveer.ms1.dto.LoanApplicationRequestDto;
import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.Client;
import credit.conveer.msDeal.model.Credit;

import java.util.List;

public interface CreateClienService {

    List<LoanOfferDto> createClientAndApplication(LoanApplicationRequestDto dto);

    Application createApplication(Client client);

    Client createClient(LoanApplicationRequestDto dto);

    Credit createCredot(Application application, LoanApplicationRequestDto dto);
}
