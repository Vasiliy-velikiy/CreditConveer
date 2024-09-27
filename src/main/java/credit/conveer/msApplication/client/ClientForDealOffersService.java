package credit.conveer.msApplication.client;


import credit.conveer.msApplication.dto.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(name = "ClientForDealOffers", url ="${urlForDealServiceAOffers}")
public interface ClientForDealOffersService {

    @PostMapping
    void calculateOffers(LoanOfferDto dto);
}
