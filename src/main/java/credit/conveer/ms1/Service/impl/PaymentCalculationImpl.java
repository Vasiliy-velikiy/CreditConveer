package credit.conveer.ms1.Service.impl;


import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.ms1.Service.PaymentCalculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
вычисление всех платежей,страховки,ставки.
 */
@Service
@Slf4j
public class PaymentCalculationImpl implements PaymentCalculation {

    @Value("${conveyor.percent}")
    private Integer percent;

    @Value("${conveyor.monthly}")
    private Integer monthly;

    @Value("${conveyor.dayOfYear}")
    private Integer dayOfYear;

    @Value("${conveyor.dayOfMonth}")
    private Integer dayOfMonth;

    /*
расчет ежемесечного платежа
https://www.raiffeisen.ru/wiki/kak-rasschitat-annuitetnyj-platezh/
*/
    @Override
    public BigDecimal getMonthlyPayment(ScoringDataDTO scoringDataDTO) {
        // расчет мессячной проентной ставки
        BigDecimal monthlyRate = BigDecimal.valueOf((getRate(scoringDataDTO).doubleValue() * percent) / (percent * monthly)).setScale(4, RoundingMode.HALF_EVEN);
        //
        if (scoringDataDTO.getIsInsuranceEnabled()) {
            return BigDecimal.valueOf((scoringDataDTO.getAmount().doubleValue() + getInsurance(scoringDataDTO).doubleValue())
                    * (monthlyRate.doubleValue() / (1 - Math.pow((1 + monthlyRate.doubleValue()), -scoringDataDTO.getTerm())))).setScale(4, RoundingMode.HALF_EVEN);
        }

        return BigDecimal.valueOf(scoringDataDTO.getAmount().doubleValue()
                * (monthlyRate.doubleValue() / (1 - Math.pow((1 + monthlyRate.doubleValue()), -scoringDataDTO.getTerm())))).setScale(4, RoundingMode.HALF_EVEN);
    }

    //    Страховка считается от суммы кредита и равна 10 процентам от суммы займа
    @Override
    public BigDecimal getInsurance(ScoringDataDTO scoringDataDTO) {
        return scoringDataDTO.getAmount().divide(BigDecimal.valueOf(percent)).multiply(BigDecimal.valueOf(10));
    }

    //Полная стоимость кредита
    @Override
    public BigDecimal getTotalAmount(ScoringDataDTO scoringDataDTO) {
        return getMonthlyPayment(scoringDataDTO).multiply(new BigDecimal(scoringDataDTO.getTerm()));
    }

    //https://кредит-онлайн.рф/stati/sovety-zaemshchiku/polnaya-stoimost-potrebitelskogo-kredita/#c
    @Override
    public BigDecimal getPsk(ScoringDataDTO scoringDataDTO) {
        if (scoringDataDTO.getIsInsuranceEnabled()) {
            return (getTotalAmount(scoringDataDTO)
                    .divide(scoringDataDTO.getAmount().add(getInsurance(scoringDataDTO)), 6, RoundingMode.HALF_EVEN)
                    .subtract(BigDecimal.valueOf(1)))
                    .multiply(BigDecimal.valueOf(dayOfYear / (scoringDataDTO.getTerm() * dayOfMonth) * percent)).setScale(4, RoundingMode.HALF_EVEN);
        }
        return (getTotalAmount(scoringDataDTO)
                .divide(scoringDataDTO.getAmount())
                .subtract(BigDecimal.valueOf(1)))
                .multiply(BigDecimal.valueOf(dayOfYear / (scoringDataDTO.getTerm() * dayOfMonth) * percent)).setScale(4, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getRate(ScoringDataDTO scoringDataDTO) {
        log.debug("высчитывание % ставки");
        // проверка по рабочему статусу
        BigDecimal rate = new BigDecimal("0.10");

        switch (scoringDataDTO.getEmployment().getEmploymentStatus().name()) {
            case "UNEMPLOYED":
                throw new IllegalArgumentException("Введите корректные данные");
            case "SELF_EMPLOYED":
                rate = rate.add(BigDecimal.valueOf(0.01));
                break;
            case "BUSINESS_OWNER":
                rate = rate.add(BigDecimal.valueOf(0.03));
                break;
            case "EMPLOYED":
                rate=rate.add(BigDecimal.valueOf(0.02));
                break;
        }
        // проверка по позиции на работе
        switch (scoringDataDTO.getEmployment().getPosition().name()) {
            case "MID_MANAGER" :
                rate = rate.subtract(BigDecimal.valueOf(0.02));
                break;
            case "TOP_MANAGER":
                rate = rate.subtract(BigDecimal.valueOf(0.04));
                break;
            case "WORKER"  :
                rate=rate.subtract(BigDecimal.valueOf(0.03));
                break;
            case "OWNER":
                rate = rate.subtract(BigDecimal.valueOf(0.05));
                break;
            // проверка
        }
        // проверка по сумме займа
        if (scoringDataDTO.getAmount().intValue() > scoringDataDTO.getEmployment().getSalary().intValue() * 20) {
            throw new IllegalArgumentException("Сумма займа больше 20 зарплат");
        }
        // проверка по семейному положению
        switch (scoringDataDTO.getMaritalStatus().name()) {
            case "MARRIED":
                rate = rate.subtract(BigDecimal.valueOf(0.03));
                break;
            case "DIVORCED":
                rate = rate.add(BigDecimal.valueOf(0.01));
                break;
            case "SINGLE":
                rate = rate.add(BigDecimal.valueOf(0.02));
                break;
            case "WIDOW_WIDOWER":
                rate = rate.add(BigDecimal.valueOf(0.04));
                break;

        }
        // проверка по кол-ву иждевенцев
        if (scoringDataDTO.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.valueOf(0.01));
        }
        // проверка по возврасту
        if (scoringDataDTO.getBirthdate().getDayOfYear() <= 18 &&
                scoringDataDTO.getBirthdate().getDayOfYear() >= 60) {
            throw new IllegalArgumentException("Возраст должен быть от 18 до 60");
        }
        // проверка по полу
        switch (scoringDataDTO.getGender().name()) {
            case "WOMAN" :
                if (scoringDataDTO.getBirthdate().getDayOfYear() <= 35 &&
                        scoringDataDTO.getBirthdate().getDayOfYear() >= 60) {
                    rate = rate.subtract(BigDecimal.valueOf(0.03));
                }
                break;
            case "MAN" :
                if(scoringDataDTO.getBirthdate().getDayOfYear() <= 30 &&
                        scoringDataDTO.getBirthdate().getDayOfYear() >= 55 ){
                    rate = rate.subtract(BigDecimal.valueOf(0.03));
                }
                break;
            case "NOT_BINARY" :
                rate = rate.add(BigDecimal.valueOf(0.03));

        }
        // проверка по стажу работы
        if (scoringDataDTO.getEmployment().getWorkExperienceTotal() < 12 ||
                scoringDataDTO.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new IllegalArgumentException("Некоректный стаж");
        }
        log.debug("rate is= "+rate);
        return rate;
    }
}

