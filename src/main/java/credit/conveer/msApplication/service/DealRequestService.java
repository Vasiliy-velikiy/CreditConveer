package credit.conveer.msApplication.service;

import credit.conveer.msApplication.dto.LoanApplicationRequestDto;
import credit.conveer.msApplication.dto.LoanOfferDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DealRequestService {

    List<LoanOfferDto> prescoringAndEvaluateRules( LoanApplicationRequestDto dto);
}
