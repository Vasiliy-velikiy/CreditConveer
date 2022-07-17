package credit.conveer.msDeal.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
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

//    @Column(name = "applicationId")
//    Long applicationId;

    @Column(name = "requestedAmount")
    BigDecimal requestedAmount;

    @Column(name = "totalAmount")
    BigDecimal totalAmount;

    @Column(name = "term")
    Integer term; //срок

    @Column(name = "monthlyPayment")
    BigDecimal monthlyPayment;

    @Column(name = "rate")
    BigDecimal rate; //ставка

    @Column(name = "isInsuranceEnabled")
    Boolean isInsuranceEnabled; //страховка

    @Column(name = "isSalaryClient")
    Boolean isSalaryClient;    //клиент на зарплате


    @OneToOne(optional = false)
    @JoinColumn(name="application_id")
    Application applicationWithLoan;
}
