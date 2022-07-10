package credit.conveer.ms1.Service.impl;

import credit.conveer.ms1.Dto.PaymentScheduleElement;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.ms1.Service.PaymentSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PaymentScheduleImpl implements PaymentSchedule {
    private PaymentCalculationImpl paymentCalculation;

    public PaymentScheduleImpl(PaymentCalculationImpl paymentCalculation) {
        this.paymentCalculation = paymentCalculation;
    }

    @Override
    public List<PaymentScheduleElement> schedule(ScoringDataDTO scoringDataDTO) {
        log.debug("schedule of month payments");
        // размер - кол-во месяцев + 1,т.к. 1 месяц выдачи кредита не имеет выплат
        List<PaymentScheduleElement> result = new ArrayList<>(scoringDataDTO.getTerm() + 1);
        // создание расписания, присваение номера платежа, установка даты,
        // проверка если клиет со страховкой в total payment добавлем
        // страховку, debt payment(размер выплаты долга) неизменный во время всей выплаты
        //
        for (int i = 0; i <= scoringDataDTO.getTerm(); i++) {
            if (i == 0) {
                result.add(i, new PaymentScheduleElement()
                        .setNumber(i)
                        .setDate(LocalDate.now().plusMonths(i))
                        .setTotalPayment(paymentCalculation.getTotalAmount(scoringDataDTO))
                        .setDebtPayment(paymentCalculation.getMonthlyPayment(scoringDataDTO))
                        .setRemainingDebt(paymentCalculation.getTotalAmount(scoringDataDTO))
                        .setInterestPayment(BigDecimal.valueOf(0)));

            } else {
                result.add(i, new PaymentScheduleElement()
                        .setNumber(i)
                        .setDate(LocalDate.now().plusMonths(i))
                        .setTotalPayment(paymentCalculation.getTotalAmount(scoringDataDTO))
                        .setDebtPayment(paymentCalculation.getMonthlyPayment(scoringDataDTO))
                        .setRemainingDebt( paymentCalculation.getTotalAmount(scoringDataDTO)
                                .divide(paymentCalculation.getMonthlyPayment(scoringDataDTO)
                                        .multiply(BigDecimal.valueOf(i)), 4, RoundingMode.HALF_EVEN))
                        .setInterestPayment(BigDecimal.valueOf((paymentCalculation.getRate(scoringDataDTO).doubleValue() * 100 / 12 + 1) *
                                paymentCalculation.getTotalAmount(scoringDataDTO)
                                        .divide(paymentCalculation.getMonthlyPayment(scoringDataDTO)
                                                .multiply(BigDecimal.valueOf(i)), 4, RoundingMode.HALF_EVEN).doubleValue())));
            }
        }
        log.debug("List of schedule elements is = "+result.toString());
        return result;
    }
}