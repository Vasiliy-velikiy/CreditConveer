package credit.conveer.msDeal.Service;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.Client;
import credit.conveer.msDeal.Model.Credit;

import java.util.List;

public interface CreateClienService {

    List<LoanOfferDTO> createClientAndApplication(LoanApplicationRequestDTO dto);

    Application createApplication(Client client);

    Client createClient(LoanApplicationRequestDTO dto);

    Credit createCredot(Application application, LoanApplicationRequestDTO dto);
}
