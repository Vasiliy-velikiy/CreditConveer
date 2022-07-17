package credit.conveer.msDeal.Model;

import credit.conveer.ms1.Dto.PaymentScheduleElement;
import credit.conveer.msDeal.Model.Enum.CreditStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;

/**
 * кредит
 */
@Entity
@Table(name = "credit")
@Getter
@Setter
@Accessors(chain = true)
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;   // Сумма
    private Integer term;      // Срок
    private BigDecimal monthly_payment; // (Ежемесячный платеж)
    private BigDecimal rate;     // (Процентная ставка)
    private BigDecimal psk;     // (Полная стоимость кредита)

    //private PaymentScheduleElement payment_schedule; // (График платежей

    // Доп. услуги
    private Boolean is_insurance_enabled;  //(Страховка включена?)
    private Boolean is_salary_client;     //(Зарплатный клиент?)

    @OneToOne()
    @JoinColumn(name = "application_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Application application;

    @Column(name = "status")
    @Enumerated(STRING)
    private CreditStatus credit_status;          // Статус кредита CALCULATED ISSUED

}
