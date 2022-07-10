package credit.conveer.ms1.Service.impl;

import credit.conveer.ms1.Dto.CreditDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.ms1.Service.CreditCalculation;
import credit.conveer.ms1.Service.PaymentSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class CreditCalculationImpl implements CreditCalculation {

    private final PaymentCalculationImpl paymentCalculation;

    private final PaymentScheduleImpl paymentSchedule;

    public CreditCalculationImpl(PaymentCalculationImpl paymentCalculation, PaymentScheduleImpl paymentSchedule) {
        this.paymentCalculation = paymentCalculation;
        this.paymentSchedule = paymentSchedule;
    }

    @Override
    public CreditDTO getCalculatedOffers(ScoringDataDTO scoringDataDTO) {
        log.info("Создание CreditDTO наполненого всеми параметрами расчета");

        BigDecimal rate = paymentCalculation.getRate(scoringDataDTO);
       CreditDTO creditDTO= new CreditDTO().setAmount(paymentCalculation.getTotalAmount(scoringDataDTO))
                .setTerm(scoringDataDTO.getTerm())
                .setMonthlyPayment(paymentCalculation.getMonthlyPayment(scoringDataDTO))
                .setRate(rate)
                .setPsk(paymentCalculation.getPsk(scoringDataDTO))
                .setIsInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled())
                .setIsSalaryClient(scoringDataDTO.getIsSalaryClient())
                .setPaymentSchedule(paymentSchedule.schedule(scoringDataDTO));
        log.debug("credit dto is= "+creditDTO.toString());
      return creditDTO;

    }
}
