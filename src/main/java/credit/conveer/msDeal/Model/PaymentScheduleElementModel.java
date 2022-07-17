package credit.conveer.msDeal.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
/**графика ежемесячный платежей на подобии дто*/
@Entity
@Table(name = "PaymentSchedule")
@Getter
@Setter
@Accessors(chain = true)
public class PaymentScheduleElementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Integer number;

    LocalDate date;

    BigDecimal totalPayment;   //всего к оплате

    BigDecimal interestPayment; //Выплата процентов

    BigDecimal debtPayment;    //долг

    BigDecimal remainingDebt;  //остаток долга

    @ManyToOne()
    @JoinColumn(name = "credit_id")
    Credit creditShed;
}
