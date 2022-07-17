package credit.conveer.msDeal.Dto;


import credit.conveer.msDeal.Model.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Класс истории изменения заявкм
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "ApplicationStatusHistoryDTO ", description = "contains information about the history")
public class ApplicationStatusHistoryDTO {
    @Schema(description = "status of application")
    Status status;
    @Schema(description = "time")
    LocalDateTime time;
    @Schema(description = "changeType")
    Enum changeType;
}
