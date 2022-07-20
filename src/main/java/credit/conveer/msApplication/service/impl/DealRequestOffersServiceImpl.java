package credit.conveer.msApplication.service.impl;

import credit.conveer.msApplication.client.ClientForDealOffersService;
import credit.conveer.msApplication.dto.LoanOfferDto;
import credit.conveer.msApplication.service.DealRequestOffersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DealRequestOffersServiceImpl implements DealRequestOffersService {

    public ClientForDealOffersService clientForDealOffersService;

    public void selectOneOfOffers(LoanOfferDto dto) {
        log.info("enter LoanOfferDto in DealRequestOffersServiceImpl   is ={} ", dto);
        clientForDealOffersService.calculateOffers(dto);
    }
}
