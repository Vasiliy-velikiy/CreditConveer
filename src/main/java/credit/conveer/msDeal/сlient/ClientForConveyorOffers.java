package credit.conveer.msDeal.сlient;

import credit.conveer.ms1.dto.LoanApplicationRequestDto;
import credit.conveer.ms1.dto.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "ClientForConveyorOffers", url = "${urlForConveyorOffer}")
public interface ClientForConveyorOffers {

   @PostMapping
     List<LoanOfferDto> touchOffers(LoanApplicationRequestDto loanApplicationRequestDTO);

}
