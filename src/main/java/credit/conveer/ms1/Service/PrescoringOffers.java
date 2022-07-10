package credit.conveer.ms1.Service;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public interface PrescoringOffers {
    BigDecimal getMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term, boolean insurence);

    BigDecimal getInsurance(BigDecimal amount);

    BigDecimal getTotalAmount(BigDecimal amount, BigDecimal rate, Integer term, boolean insurence);

    List<LoanOfferDTO> getPrescoringOffers(LoanApplicationRequestDTO loanApplicationRequestDTO);
}
