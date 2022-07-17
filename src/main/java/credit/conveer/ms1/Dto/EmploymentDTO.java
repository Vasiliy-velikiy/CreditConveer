package credit.conveer.ms1.Dto;

import credit.conveer.msDeal.Model.Enum.EmploymentStatus;
import credit.conveer.msDeal.Model.Enum.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
/**Класс описывающий опыт работы(трудоустроуства)*/
@Data
@Accessors(chain = true)
@Schema(name = "EmploymentDTO", description = "contains information about the substitute")
public class EmploymentDTO {
    @Schema(description = "employmentStatus")
    EmploymentStatus employmentStatus;
    @Schema(description = "employerINN")
    String employerINN;
    @Schema(description = "salary")
    BigDecimal salary;
    @Schema(description = "job title")
    Position position;
    @Schema(description = "workExperienceTotal")
    Integer workExperienceTotal;
    @Schema(description = "workExperienceCurrent")
    Integer workExperienceCurrent;
}

