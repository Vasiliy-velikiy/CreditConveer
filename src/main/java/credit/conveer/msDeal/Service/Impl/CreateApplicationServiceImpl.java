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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CreateApplicationServiceImpl implements CreateApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ClientForCalculationScoring clientForCalculationScoring;
    private final EmploymentRepository employmentRepository;
    private final ClientRepository clientRepository;

    Long employmentId = Long.valueOf(0);

    public CreateApplicationServiceImpl(ApplicationRepository applicationRepository, ClientForCalculationScoring clientForCalculationScoring, EmploymentRepository employmentRepository, ClientRepository clientRepository) {
        this.applicationRepository = applicationRepository;
        this.clientForCalculationScoring = clientForCalculationScoring;
        this.employmentRepository = employmentRepository;
        this.clientRepository = clientRepository;
    }

    public void completionOfRegistrationAndFullCreditCalculation(FinishRegistrationRequestDTO dto, Long applicationId) {
        log.info("send dto and id to feignClient " + "id =" + applicationId + " ,dto is= " + dto.toString());
        List<Application> applicationList = applicationRepository.findAll();
        List<Client>clientList=clientRepository.findAll();
        Client testClient=clientRepository.findById(Long.valueOf(1)).get();
        Optional<Application> optional = applicationRepository.findById(applicationId);
        Application application = optional.get();
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO()
                .setAmount(application.getCredit().getAmount())
                .setTerm(application.getCredit().getTerm())
                .setFirstName(application.getClient().getFirst_name())
                .setLastName(application.getClient().getLast_name())
                .setMiddleName(application.getClient().getMiddle_name())

                .setBirthdate(application.getClient().getBirth_date())
                .setPassportSeries(application.getClient().getSeries())
                .setPassportNumber(application.getClient().getPassport())
                .setIsInsuranceEnabled(application.getCredit().getIs_insurance_enabled())
                .setIsSalaryClient(application.getCredit().getIs_salary_client())
                .setGender(dto.getGender())
                .setMaritalStatus(dto.getMaritalStatus())
                .setDependentAmount(dto.getDependentAmount())
                .setPassportIssueDate(dto.getPassportIssueDate())
                .setPassportIssueBranch(dto.getPassportIssueBrach())
                .setEmployment(dto.getEmployment())
                .setAccount(dto.getAccount());

//        Employment employment = new Employment()
//                .setId(++employmentId)
//                .setEmployment_status(dto.getEmployment().getEmploymentStatus())
//                .setSalary(dto.getEmployment().getSalary())
//                .setPosition(dto.getEmployment().getPosition())
//                .setWork_experience_total(dto.getEmployment().getWorkExperienceTotal())
//                .setWork_experience_current(dto.getEmployment().getWorkExperienceCurrent());

    //    Client client = clientRepository.findById(applicationId).get();
//        employment.setClient(client);
//        employmentRepository.save(employment);

        CreditDTO creditDTO = clientForCalculationScoring.calculateScoringDTO(scoringDataDTO);
        log.info("creditDto is " + creditDTO.toString());

    }
}
