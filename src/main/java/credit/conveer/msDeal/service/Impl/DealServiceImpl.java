package credit.conveer.msDeal.service.Impl;

import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.ApplicationStatusHistory;
import credit.conveer.msDeal.model.Enum.ChangeType;
import credit.conveer.msDeal.model.Enum.Status;
import credit.conveer.msDeal.repository.ApplicationRepository;
import credit.conveer.msDeal.repository.ApplicationStatusHistoryRepository;
import credit.conveer.msDeal.service.DealService;
import credit.conveer.msDeal.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {


    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;

    public void selectOneOfOffers(LoanOfferDto dto) {
        log.info("enter LoanOfferDTO is ={}",dto);
        Application application = fetchApplFromRepo(dto);
        ApplicationStatusHistory applicationStatusHistory = createApplStatusHistory(application);

        applicationRepository.save(application);
        applicationStatusHistoryRepository.save(applicationStatusHistory);
        applicationRepository.save(application);

    }

    public ApplicationStatusHistory createApplStatusHistory(Application application) {
        log.info("enter application is ={}",application);
        ApplicationStatusHistory applicationStatusHistory = new ApplicationStatusHistory()
                .setTime(LocalDateTime.now())
                .setStatus(application.getStatus())
                .setChangeType(ChangeType.CHANGED)
                .setApplication(application);
        log.info("return object applicationStatusHistory is ={}", applicationStatusHistory);

        return applicationStatusHistory;
    }

    public Application fetchApplFromRepo(LoanOfferDto dto) {
        log.info("enter LoanOfferDto is ={}",dto);
        List<Application> applicationList = applicationRepository.findAll();
        Optional<Application> optional = applicationRepository.findById(dto.getApplicationId());
        Application application = optional.get();

        application.setStatus(Status.APPROVED);
        application.setAppliedOffer(LoanMapper.toEntity(dto).setApplicationWithLoan(application)/*.setId(++loanId))*/);

        log.info("return object application is ={}", application);
        return application;
    }


}
