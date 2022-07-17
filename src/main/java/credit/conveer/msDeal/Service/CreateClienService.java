package credit.conveer.msDeal.Service;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;

import java.util.List;

public interface CreateClienService {

    List<LoanOfferDTO> createClientAndApplication(LoanApplicationRequestDTO dto);
}
