package credit.conveer.msApplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс дто -Заявка на получение кредита
 */
@Data
@Accessors(chain = true)
@Builder
@Jacksonized
@Schema(name = "LoanApplicationRequestDTO", description = "contains information about the loan application")
public class LoanApplicationRequestDto {
    @Schema(description = "amount")
    @Min(value = 10000)
    public BigDecimal amount;  //сумма кредита

    @Schema(description = "term")
    @Min(value = 6)
    public Integer term;  //срок

    @Schema(description = "firstName")
    @Size(min = 2, max = 30)
    public String firstName;

    @Schema(description = "lastName")
    @Size(min = 2, max = 30)
    public String lastName;

    @Schema(description = "middleName")
    @Size(min = 2, max = 30)
    public String middleName;

    @Schema(description = "email")
    @Pattern(regexp = "[\\w\\.]{2,50}@[\\w\\.]{2,20}")
    public String email;

    @Schema(description = "birthdate")
    @Past
    @Pattern(regexp = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate birthdate;

    @Schema(description = "passportSeries")
    @Size(min = 4, max = 4)
    @Pattern(regexp = "^[0-9]{4}+$", message = "4 digits")
    public String passportSeries;

    @Schema(description = "passportNumber")
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[0-9]{6}+$", message = "6 digits")
    public String passportNumber;
}


