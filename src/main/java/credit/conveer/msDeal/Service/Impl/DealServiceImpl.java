package credit.conveer.msDeal.Service.Impl;

import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.ApplicationStatusHistory;
import credit.conveer.msDeal.Model.Client;
import credit.conveer.msDeal.Model.Credit;
import credit.conveer.msDeal.Model.Enum.ChangeType;
import credit.conveer.msDeal.Model.Enum.Status;
import credit.conveer.msDeal.Repository.ApplicationRepository;
import credit.conveer.msDeal.Repository.ApplicationStatusHistoryRepository;
import credit.conveer.msDeal.Repository.ClientRepository;
import credit.conveer.msDeal.Repository.CreditRepository;
import credit.conveer.msDeal.Service.DealService;
import credit.conveer.msDeal.mapper.LoanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class DealServiceImpl implements DealService {


    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    private final LoanMapper loanMapper;

    private Long applicationStatusHistoryId = Long.valueOf(0);
    private Long loanId = Long.valueOf(0);

    public DealServiceImpl(ApplicationRepository applicationRepository, ApplicationStatusHistoryRepository applicationStatusHistoryRepository, LoanMapper loanMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
        this.loanMapper = loanMapper;
    }

    public void selectOneOfOffers(LoanOfferDTO dto) {
        log.info("LoanOfferDTO is"+dto.toString());
        //хибер не хочет искать значения по findById пока не подтяну все из базы
        List<Application> applicationList = applicationRepository.findAll();
        Optional<Application> optional = applicationRepository.findById(dto.getApplicationId());
        Application application = optional.get();


        application.setStatus(Status.APPROVED);
        application.setAppliedOffer(loanMapper.toEntity(dto).setId(++loanId));

        log.info(" application is"+application);
        ApplicationStatusHistory applicationStatusHistory = new ApplicationStatusHistory().
                setId(++applicationStatusHistoryId)
                .setTime(LocalDateTime.now())
                .setStatus(application.getStatus())
                .setChangeType(ChangeType.CHANGED)
                .setApplication(application);
        log.info("applicationStatusHistory is"+applicationStatusHistory.toString());

        applicationRepository.save(application);
        applicationStatusHistoryRepository.save(applicationStatusHistory);

        applicationRepository.save(application);

    }
}
