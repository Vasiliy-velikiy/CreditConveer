package credit.conveer.ms1.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**дто на кредитные предложение */
@Data
@Accessors(chain = true)
@Builder
@Schema(name = "LoanOfferDTO ", description = "dto on loan offer")
public class LoanOfferDTO {
    @Schema(description = "applicationId")
    Long applicationId;

    @Schema(description = "requestedAmount")
    BigDecimal requestedAmount;

    @Schema(description = "totalAmount")
    BigDecimal totalAmount;

    @Schema(description = "term")
    Integer term; //срок

    @Schema(description = "monthlyPayment")
    BigDecimal monthlyPayment;

    @Schema(description = "rate")
    BigDecimal rate; //ставка

    @Schema(description = "isInsuranceEnabled")
    Boolean isInsuranceEnabled; //страховка

    @Schema(description = "isSalaryClient")
    Boolean isSalaryClient;    //клиент на зарплате
}