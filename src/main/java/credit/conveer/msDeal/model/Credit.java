package credit.conveer.msDeal.model;

import credit.conveer.msDeal.model.Enum.CreditStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
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

    @OneToMany(mappedBy = "creditShed",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = {PERSIST})
    private List <PaymentScheduleElementModel> payment_schedule; // (График платежей

}
