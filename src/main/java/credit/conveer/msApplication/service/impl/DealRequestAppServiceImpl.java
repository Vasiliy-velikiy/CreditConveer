package credit.conveer.msApplication.service.impl;

import credit.conveer.msApplication.client.ClientForDealApplicationService;
import credit.conveer.msApplication.dto.LoanApplicationRequestDto;
import credit.conveer.msApplication.dto.LoanOfferDto;
import credit.conveer.msApplication.service.DealRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DealRequestAppServiceImpl implements DealRequestService {

    private ClientForDealApplicationService clientForDealService;

    public List<LoanOfferDto> prescoringAndEvaluateRules(LoanApplicationRequestDto dto){
        log.info("enter LoanApplicationRequestDTO is ={} ",dto);
        List<LoanOfferDto> offerDtoList=  clientForDealService.calculateOffers(dto);
        log.info("return list of offers LoanApplicationRequestDTO is ={} ",offerDtoList);
        return offerDtoList;
    }
}
