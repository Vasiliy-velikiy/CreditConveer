package credit.conveer.msDeal.dto;


import credit.conveer.ms1.dto.EmploymentDto;
import credit.conveer.msDeal.model.Enum.Gender;
import credit.conveer.msDeal.model.Enum.MaritalStatus;
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
public class FinishRegistrationRequestDto {
    @Schema(description = "gender")
    private Gender gender;
    @Schema(description = "maritalStatus")
    private MaritalStatus maritalStatus;
    @Schema(description = "dependentAmount")
    private Integer dependentAmount;
    @Schema(description = "passportIssueDate")
    private LocalDate passportIssueDate;
    @Schema(description = "passportIssueBrach")
    private String passportIssueBrach;
    @Schema(description = "employment")
    private EmploymentDto employment;
    @Schema(description = "account")
    private String account;
}
