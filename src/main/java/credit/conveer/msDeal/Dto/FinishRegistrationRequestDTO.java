package credit.conveer.msDeal.Dto;


import credit.conveer.ms1.Dto.EmploymentDTO;
import credit.conveer.msDeal.Model.Enum.Gender;
import credit.conveer.msDeal.Model.Enum.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * Класс дто-завершение регистрации
 */
@Getter
@Setter
@Accessors(chain = true) //позволяет создавать объект через сетеры в стиле билдера
@Schema(name = "FinishRegistrationRequestDTO", description = "contains information about the loan")
public class FinishRegistrationRequestDTO {
    @Schema(description = "gender")
    Gender gender;
    @Schema(description = "maritalStatus")
    MaritalStatus maritalStatus;
    @Schema(description = "dependentAmount")
    Integer dependentAmount;
    @Schema(description = "passportIssueDate")
    LocalDate passportIssueDate;
    @Schema(description = "passportIssueBrach")
    String passportIssueBrach;
    @Schema(description = "employment")
    EmploymentDTO employment;
    @Schema(description = "account")
    String account;
}
