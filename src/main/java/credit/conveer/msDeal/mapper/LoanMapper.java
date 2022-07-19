package credit.conveer.msDeal.mapper;

import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.LoanOffer;
import credit.conveer.msDeal.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
public class LoanMapper {

    public static LoanOffer toEntity(LoanOfferDto dto) {
        log.info("enter LoanOfferDto is ={}",dto);
        LoanOffer entity = new LoanOffer()
                .setRequestedAmount(dto.getRequestedAmount())
                .setTerm(dto.getTerm())
                .setMonthlyPayment(dto.getMonthlyPayment())
                .setRate(dto.getRate())
                .setIsInsuranceEnabled(dto.getIsInsuranceEnabled())
                .setIsSalaryClient(dto.getIsSalaryClient());
        log.info("convert and return to LoanOffer is ={}",dto);
        return entity;
    }


    public static LoanOfferDto toDto(LoanOffer entity) {
        log.info("enter LoanOffer  is = {}",entity);
        LoanOfferDto loanOfferDto= LoanOfferDto.builder().requestedAmount(entity.getRequestedAmount()).term(entity.getTerm()).monthlyPayment(entity.getMonthlyPayment())
                .rate(entity.getRate()).isInsuranceEnabled(entity.getIsInsuranceEnabled()).isSalaryClient(entity.getIsSalaryClient()).applicationId(entity.getApplicationWithLoan().getId()).build();
        log.info("convert and return to LoanOffer is ={}",loanOfferDto);
        return loanOfferDto;
    }
}
