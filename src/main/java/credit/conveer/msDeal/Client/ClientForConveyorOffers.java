package credit.conveer.msDeal.Client;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "ClientForConveyorOffers", url = "${urlForConveyorOffer}")
public interface ClientForConveyorOffers {


   @PostMapping
     List<LoanOfferDTO> touchOffers( LoanApplicationRequestDTO loanApplicationRequestDTO);

}
