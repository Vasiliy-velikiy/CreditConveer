package credit.conveer.msDeal.model;

import credit.conveer.msDeal.model.Enum.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
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

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client clientApp;//Клиент

    @OneToOne( mappedBy="application", fetch = FetchType.LAZY)
    private Credit credit; //Кредит

    @Column(name = "status")
    @Enumerated(STRING)
    private Status status; //Статус

    @Column(name = "creation_date")
    private LocalDate creation_date; //Дата создания

    @OneToOne( mappedBy="applicationWithLoan")
    private LoanOffer appliedOffer;//Принятое предложение кредита

    @Column(name = "sign_date")
    private LocalDate sign_date; // Дата подписания
    @Column(name = "ses_code")
    private String ses_code; //Код ПЭП (Простая Электронная Подпись

    @OneToMany(mappedBy = "application",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = {PERSIST})
    private List<ApplicationStatusHistory> status_history;//(История изменения статусов)

    }

