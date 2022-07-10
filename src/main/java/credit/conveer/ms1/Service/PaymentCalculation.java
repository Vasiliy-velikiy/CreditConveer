package credit.conveer.ms1.Service;

import credit.conveer.ms1.Dto.ScoringDataDTO;

import java.math.BigDecimal;

public interface PaymentCalculation {

    BigDecimal getMonthlyPayment(ScoringDataDTO scoringDataDTO);

    BigDecimal getInsurance(ScoringDataDTO scoringDataDTO);

    BigDecimal getTotalAmount(ScoringDataDTO scoringDataDTO);

    BigDecimal getPsk(ScoringDataDTO scoringDataDTO);

    BigDecimal getRate(ScoringDataDTO scoringDataDTO);
}
