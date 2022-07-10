package credit.conveer.ms1.Service;

import credit.conveer.ms1.Dto.PaymentScheduleElement;
import credit.conveer.ms1.Dto.ScoringDataDTO;

import java.util.List;

public interface PaymentSchedule {

    List<PaymentScheduleElement> schedule(ScoringDataDTO scoringDataDTO);
}

