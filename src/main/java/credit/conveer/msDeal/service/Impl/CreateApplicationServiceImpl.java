package credit.conveer.msDeal.service.Impl;

import credit.conveer.ms1.dto.CreditDto;
import credit.conveer.ms1.dto.ScoringDataDto;
import credit.conveer.msDeal.сlient.ClientForCalculationScoring;
import credit.conveer.msDeal.dto.FinishRegistrationRequestDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.Client;
import credit.conveer.msDeal.model.Employment;
import credit.conveer.msDeal.repository.ApplicationRepository;
import credit.conveer.msDeal.repository.ClientRepository;
import credit.conveer.msDeal.repository.EmploymentRepository;
import credit.conveer.msDeal.service.CreateApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateApplicationServiceImpl implements CreateApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ClientForCalculationScoring clientForCalculationScoring;
    private final EmploymentRepository employmentRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public void completionOfRegistrationAndFullCreditCalculation(FinishRegistrationRequestDto dto, Long applicationId) {
        log.info("send dto and id to feignClient = {} , dto is = {} ", applicationId,dto);
        Optional<Application> optional = applicationRepository.findById(applicationId);
        Application application = optional.get();
        ScoringDataDto scoringDataDTO = createScoringDataDto(application, dto);

        Employment employment = createEmployment(dto);

        Client clientForSetEmpl = clientRepository.findById(applicationId).get();
        clientForSetEmpl.setEmployment(employment);
        clientRepository.save(clientForSetEmpl);
        employment.setClientEmpl(clientForSetEmpl);
        employmentRepository.save(employment);

        CreditDto creditDTO = clientForCalculationScoring.calculateScoringDTO(scoringDataDTO);
        log.info("creditDto is = {} ", creditDTO);

    }

    public ScoringDataDto createScoringDataDto(Application application, FinishRegistrationRequestDto dto) {
        log.info("application is = {} , finishRegDto is = {} ",application,dto);
        ScoringDataDto scoringDataDto = new ScoringDataDto()
                .setAmount(application.getCredit().getAmount())
                .setTerm(application.getCredit().getTerm())
                .setFirstName(application.getClientApp().getFirst_name())
                .setLastName(application.getClientApp().getLast_name())
                .setMiddleName(application.getClientApp().getMiddle_name())

                .setBirthdate(application.getClientApp().getBirth_date())
                .setPassportSeries(application.getClientApp().getSeries())
                .setPassportNumber(application.getClientApp().getPassport())
                .setIsInsuranceEnabled(application.getCredit().getIs_insurance_enabled())
                .setIsSalaryClient(application.getCredit().getIs_salary_client())
                .setGender(dto.getGender())
                .setMaritalStatus(dto.getMaritalStatus())
                .setDependentAmount(dto.getDependentAmount())
                .setPassportIssueDate(dto.getPassportIssueDate())
                .setPassportIssueBranch(dto.getPassportIssueBrach())
                .setEmployment(dto.getEmployment())
                .setAccount(dto.getAccount());

        log.info("scoringDataDTO is ={} ",scoringDataDto);
        return scoringDataDto;
    }

    public Employment createEmployment(FinishRegistrationRequestDto dto) {
        log.info("FinishRegistrationRequestDto is ={} ",dto);
        Employment employment = new Employment()
                .setEmployment_status(dto.getEmployment().getEmploymentStatus())
                .setSalary(dto.getEmployment().getSalary())
                .setPosition(dto.getEmployment().getPosition())
                .setWork_experience_total(dto.getEmployment().getWorkExperienceTotal())
                .setWork_experience_current(dto.getEmployment().getWorkExperienceCurrent());
        log.info("object employment is ={} ",employment);
        return employment;
    }

}