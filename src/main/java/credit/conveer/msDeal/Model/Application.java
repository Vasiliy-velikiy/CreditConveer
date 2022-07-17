package credit.conveer.msDeal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.ms1.Dto.PaymentScheduleElement;
import credit.conveer.msDeal.Dto.ApplicationStatusHistoryDTO;
import credit.conveer.msDeal.Model.Enum.CreditStatus;
import credit.conveer.msDeal.Model.Enum.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;

/**
 * класс заявка
 */
@Entity
@Table(name = "application")
@Getter
@Setter
@Accessors(chain = true)
public class Application {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, DETACH, REFRESH})
    @JoinColumn(name = "client_id")
    private Client client;//Клиент

    @OneToOne(optional = false, mappedBy="application", fetch = FetchType.LAZY)
    private Credit credit; //Кредит


    @Column(name = "status")
    @Enumerated(STRING)
    private Status status; //Статус

    @Column(name = "creation_date")
    private LocalDate creation_date; //Дата создания


    @OneToOne(optional = false, mappedBy="applicationWithLoan")
    private LoanOffer appliedOffer;//Принятое предложение кредита


    @Column(name = "sign_date")
    private LocalDate sign_date; // Дата подписания
    @Column(name = "ses_code")
    private String ses_code; //Код ПЭП (Простая Электронная Подпись


    @OneToMany(mappedBy = "application",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = {PERSIST, MERGE, DETACH, REFRESH})
    private List<ApplicationStatusHistory> status_history;//(История изменения статусов)

    }

