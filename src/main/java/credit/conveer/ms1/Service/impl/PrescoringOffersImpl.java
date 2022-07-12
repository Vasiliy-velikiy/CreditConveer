package credit.conveer.ms1.Service.impl;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.ms1.Service.PrescoringOffers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class PrescoringOffersImpl implements PrescoringOffers {
    @Value("${conveyor.loanDTO.rate}")
    private BigDecimal rate;

    private Long id= Long.valueOf(0);

    @Value("${conveyor.percent}")
    private Integer percent;

    @Value("${conveyor.monthly}")
    private Integer monthly;

    /*
    Логика прескоринга. Базовая ставка 10. Страховка 1000.
    Базовая ставка уменьшается если сумма кредита больше 1кк на 1п., больше 10кк на 2п.,
    измененная базовая ставка работает только для зарплатных клиентов.

     */
    public BigDecimal getMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term, boolean insurence) {
        log.debug("this is monthly payment");
        BigDecimal monthlyRate = BigDecimal.valueOf((rate.doubleValue() * percent) / (percent * monthly)).setScale(4, RoundingMode.HALF_EVEN);
        if (insurence) {
            BigDecimal bc= BigDecimal.valueOf((amount.doubleValue() + getInsurance(amount).doubleValue())
                    * (monthlyRate.doubleValue() / (1 - Math.pow((1 + monthlyRate.doubleValue()), -term)))).setScale(4, RoundingMode.HALF_EVEN);
            log.debug(String.valueOf(bc));
            return bc;
        }
        BigDecimal bc= BigDecimal.valueOf(amount.doubleValue()
                * (monthlyRate.doubleValue() / (1 - Math.pow((1 + monthlyRate.doubleValue()), -term)))).setScale(4, RoundingMode.HALF_EVEN);
        log.debug(String.valueOf(bc));
        return bc;
    }

    public BigDecimal getInsurance(BigDecimal amount) {
        log.debug("this is Insurance");
      BigDecimal ins= amount.divide(BigDecimal.valueOf(percent),4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(10));
        log.debug(String.valueOf(ins));
        return ins;
    }

    public BigDecimal getTotalAmount(BigDecimal amount, BigDecimal rate, Integer term, boolean insurence) {
        log.debug("this is total amount");

        BigDecimal tAm= getMonthlyPayment(amount, rate, term, insurence).multiply(new BigDecimal(term));
        log.debug(String.valueOf(tAm));
        return tAm;
    }

    @Override
    public List<LoanOfferDTO> getPrescoringOffers(LoanApplicationRequestDTO loanDTO) {
        log.debug("prescoring offers is");

        List<LoanOfferDTO> result = new ArrayList<>(4);

        if(loanDTO.getAmount().intValue() > 1000000) {
            rate = new BigDecimal("0.9");
        }
        if(loanDTO.getAmount().intValue() > 10000000) {
            rate = new BigDecimal("0.8");
        }

        result.add(new LoanOfferDTO()
                .setApplicationId(id++)
                .setRequestedAmount(loanDTO.getAmount())
                .setTotalAmount(getTotalAmount(loanDTO.getAmount().setScale(4, RoundingMode.HALF_EVEN), this.rate, loanDTO.getTerm(), false))
                .setTerm(loanDTO.getTerm())
                .setMonthlyPayment(getMonthlyPayment(loanDTO.getAmount(), this.rate, loanDTO.getTerm(), false))
                .setRate(this.rate)
                .setIsInsuranceEnabled(false)
                .setIsSalaryClient(false));

        result.add(new LoanOfferDTO()
                .setApplicationId(id++)
                .setRequestedAmount(loanDTO.getAmount())
                .setTotalAmount(getTotalAmount(loanDTO.getAmount().setScale(4, RoundingMode.HALF_EVEN), rate, loanDTO.getTerm(), true)
                        .add(getInsurance(loanDTO.getAmount())))
                .setTerm(loanDTO.getTerm())
                .setMonthlyPayment(getMonthlyPayment(loanDTO.getAmount(), rate, loanDTO.getTerm(), true))
                .setRate(this.rate)
                .setIsInsuranceEnabled(true)
                .setIsSalaryClient(false));

        result.add(new LoanOfferDTO()
                .setApplicationId(id++)
                .setRequestedAmount(loanDTO.getAmount())
                .setTotalAmount(getTotalAmount(loanDTO.getAmount().setScale(4, RoundingMode.HALF_EVEN), rate, loanDTO.getTerm(), true)
                        .add(getInsurance(loanDTO.getAmount())))
                .setTerm(loanDTO.getTerm())
                .setMonthlyPayment(getMonthlyPayment(loanDTO.getAmount(), rate, loanDTO.getTerm(), true))
                .setRate(rate)
                .setIsInsuranceEnabled(true)
                .setIsSalaryClient(true));

        result.add(new LoanOfferDTO()
                .setApplicationId(id++)
                .setRequestedAmount(loanDTO.getAmount())
                .setTotalAmount(getTotalAmount(loanDTO.getAmount().setScale(4, RoundingMode.HALF_EVEN), this.rate, loanDTO.getTerm(), false))
                .setTerm(loanDTO.getTerm())
                .setMonthlyPayment(getMonthlyPayment(loanDTO.getAmount(), this.rate, loanDTO.getTerm(), false))
                .setRate(rate)
                .setIsInsuranceEnabled(false)
                .setIsSalaryClient(true));

        result.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());

        log.debug(result.toString());
        return result;
    }
}
