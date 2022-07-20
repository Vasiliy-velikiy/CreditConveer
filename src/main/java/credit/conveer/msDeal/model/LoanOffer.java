package credit.conveer.msDeal.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "loan_offer")
@Getter
@Setter
@Accessors(chain = true)
public class LoanOffer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requestedAmount")
    private BigDecimal requestedAmount;

    @Column(name = "totalAmount")
    private BigDecimal totalAmount;

    @Column(name = "term")
    private Integer term; //срок

    @Column(name = "monthlyPayment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate; //ставка

    @Column(name = "isInsuranceEnabled")
    private Boolean isInsuranceEnabled; //страховка

    @Column(name = "isSalaryClient")
    private Boolean isSalaryClient;    //клиент на зарплате

    @OneToOne(optional = false)
    @JoinColumn(name = "application_id")
    private Application applicationWithLoan;
}
