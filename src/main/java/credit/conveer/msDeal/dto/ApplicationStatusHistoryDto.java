package credit.conveer.msDeal.dto;


import credit.conveer.msDeal.model.Enum.Status;
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
public class ApplicationStatusHistoryDto {
    @Schema(description = "status of application")
    private Status status;
    @Schema(description = "time")
    private LocalDateTime time;
    @Schema(description = "changeType")
    private Enum changeType;
}
