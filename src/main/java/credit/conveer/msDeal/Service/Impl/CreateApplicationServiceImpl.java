package credit.conveer.msDeal.Service.Impl;

import credit.conveer.ms1.Dto.CreditDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.msDeal.Client.ClientForCalculationScoring;
import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.Client;
import credit.conveer.msDeal.Model.Employment;
import credit.conveer.msDeal.Repository.ApplicationRepository;
import credit.conveer.msDeal.Repository.ClientRepository;
import credit.conveer.msDeal.Repository.EmploymentRepository;
import credit.conveer.msDeal.Service.CreateApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CreateApplicationServiceImpl implements CreateApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ClientForCalculationScoring clientForCalculationScoring;
    private final EmploymentRepository employmentRepository;
    private final ClientRepository clientRepository;

    public CreateApplicationServiceImpl(ApplicationRepository applicationRepository, ClientForCalculationScoring clientForCalculationScoring, EmploymentRepository employmentRepository, ClientRepository clientRepository) {
        this.applicationRepository = applicationRepository;
        this.clientForCalculationScoring = clientForCalculationScoring;
        this.employmentRepository = employmentRepository;
        this.clientRepository = clientRepository;
    }

    Long employmentId = Long.valueOf(1);

    @Transactional
    public void completionOfRegistrationAndFullCreditCalculation(FinishRegistrationRequestDTO dto, Long applicationId) {
        log.info("send dto and id to feignClient " + "id =" + applicationId + " ,dto is= " + dto.toString());
        Optional<Application> optional = applicationRepository.findById(applicationId);
        Application application = optional.get();
        ScoringDataDTO scoringDataDTO = createScoringDataDTO(application, dto);

        Employment employment = createEmployment(dto);

        Client clientForSetEmpl = clientRepository.findById(applicationId).get();
        clientForSetEmpl.setEmployment(employment);
        clientRepository.save(clientForSetEmpl);
        employment.setClientEmpl(clientForSetEmpl);
        employmentRepository.save(employment);

        CreditDTO creditDTO = clientForCalculationScoring.calculateScoringDTO(scoringDataDTO);
        log.info("creditDto is " + creditDTO.toString());

    }

    public ScoringDataDTO createScoringDataDTO(Application application, FinishRegistrationRequestDTO dto) {
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO()
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
        return scoringDataDTO;
    }

    public Employment createEmployment(FinishRegistrationRequestDTO dto) {
        Employment employment = new Employment()
                .setId(employmentId++)
                .setEmployment_status(dto.getEmployment().getEmploymentStatus())
                .setSalary(dto.getEmployment().getSalary())
                .setPosition(dto.getEmployment().getPosition())
                .setWork_experience_total(dto.getEmployment().getWorkExperienceTotal())
                .setWork_experience_current(dto.getEmployment().getWorkExperienceCurrent());
        return employment;
    }

}