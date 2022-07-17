package credit.conveer.msDeal.Service.Impl;

import credit.conveer.ms1.Dto.EmploymentDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.Client;
import credit.conveer.msDeal.Model.Credit;
import credit.conveer.msDeal.Model.Employment;
import credit.conveer.msDeal.Model.Enum.*;
import credit.conveer.msDeal.Service.CreateApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CreateApplicationServiceImplTest {

    @Autowired
    private CreateApplicationService createApplicationService;

    Credit mockCredit = Mockito.mock(Credit.class);
    Client mockClient = Mockito.mock(Client.class);

    private Client client = new Client().setId(Long.valueOf(1)).setFirst_name("Vasiliy").setLast_name("Moskalev").setMiddle_name("Anatolich").setBirth_date(LocalDate.of(1993, 10, 13)).setPassport("1111").setSeries("111111").setEmail("String@mail.ru").setApplications(new ArrayList<>());
    private Application expectedAppl = new Application().setId(Long.valueOf(1)).setStatus(Status.PREAPPROVAL).setClientApp(client).setClientApp(mockClient).setCredit(mockCredit);
    private EmploymentDTO expectedEmpl = new EmploymentDTO().setEmploymentStatus(EmploymentStatus.EMPLOYED).setSalary(BigDecimal.valueOf(120000).setScale(0, RoundingMode.HALF_EVEN)).setPosition(Position.MID_MANAGER).setWorkExperienceTotal(15).setWorkExperienceCurrent(4);
    private FinishRegistrationRequestDTO finishDto = new FinishRegistrationRequestDTO().setAccount("account111").setGender(Gender.MALE).setMaritalStatus(MaritalStatus.DIVORCED).setDependentAmount(3).setPassportIssueBrach("УФМС России по Воронежской обл. центрального р-а").setPassportIssueDate(LocalDate.of(2005, 03, 13)).setEmployment(expectedEmpl);


    @Test
    void createScoringDataDTO() {
        ScoringDataDTO actual = createApplicationService.createScoringDataDTO(expectedAppl, finishDto);
        assertEquals(finishDto.getPassportIssueBrach(), actual.getPassportIssueBranch());
        assertEquals(finishDto.getPassportIssueDate(), actual.getPassportIssueDate());
        assertEquals(finishDto.getAccount(), actual.getAccount());

    }

    @Test
    void createEmployment() {
        Employment actual = createApplicationService.createEmployment(finishDto);
        assertEquals(expectedEmpl.getEmploymentStatus(), actual.getEmployment_status());
        assertEquals(expectedEmpl.getPosition(), actual.getPosition());
        assertEquals(expectedEmpl.getSalary(), actual.getSalary());
    }
}