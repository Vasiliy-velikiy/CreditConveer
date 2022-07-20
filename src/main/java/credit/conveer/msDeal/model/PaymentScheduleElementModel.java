package credit.conveer.msDeal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * графика ежемесячный платежей на подобии дто
 */
@Entity
@Table(name = "PaymentSchedule")
@Getter
@Setter
@Accessors(chain = true)
public class PaymentScheduleElementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private LocalDate date;

    private BigDecimal totalPayment;   //всего к оплате

    private BigDecimal interestPayment; //Выплата процентов

    private BigDecimal debtPayment;    //долг

    private BigDecimal remainingDebt;  //остаток долга

    @ManyToOne()
    @JoinColumn(name = "credit_id")
    private Credit creditShed;
}
