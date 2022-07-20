package credit.conveer.msApplication.client;

import credit.conveer.msApplication.dto.LoanApplicationRequestDto;
import credit.conveer.msApplication.dto.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "ClientForDealApplication", url ="${urlForDealService}")
public interface ClientForDealApplicationService {

    @PostMapping
    List<LoanOfferDto> calculateOffers(LoanApplicationRequestDto dto);

}