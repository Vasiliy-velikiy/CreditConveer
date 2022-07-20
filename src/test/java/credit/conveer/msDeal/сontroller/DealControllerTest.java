package credit.conveer.msDeal.сontroller;

import credit.conveer.ms1.dto.*;
import credit.conveer.msDeal.dto.FinishRegistrationRequestDto;
import credit.conveer.msDeal.model.Application;

import static org.mockito.Mockito.*;

import credit.conveer.msDeal.model.Client;
import credit.conveer.msDeal.model.Credit;
import credit.conveer.msDeal.model.Enum.*;
import credit.conveer.msDeal.service.CreateApplicationService;
import credit.conveer.msDeal.service.CreateClienService;
import credit.conveer.msDeal.service.DealService;
import credit.conveer.msDeal.сlient.ClientForCalculationScoring;
import credit.conveer.msDeal.сlient.ClientForConveyorOffers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreateClienService createClienService;
    @MockBean
    private CreateApplicationService createApplicationService;
    @MockBean
    private DealService dealService;
    @MockBean
    private Application application;
    @MockBean
    private ClientForCalculationScoring feignForScoring;
    @MockBean
    private ClientForConveyorOffers feignForOffers;
    @MockBean
    private CreditDto creditDto;
    @MockBean
    private LoanOfferDto loanOfferDto;
    @MockBean
    Credit mockCredit;

    @Test
    void calculationOfPossibleConditions() throws Exception {
        File file = ResourceUtils.getFile("classpath:loanApplicationRequest.json");
        String loanApplicationRequestStr = new String(Files.readAllBytes(file.toPath()));

        File fileTwo = ResourceUtils.getFile("classpath:loanOffers.json");
        String loanOffers = new String(Files.readAllBytes(fileTwo.toPath()));

        LoanOfferDto loanOfferOne = LoanOfferDto.builder().build();
        LoanOfferDto loanOfferTwo = LoanOfferDto.builder().build();
        LoanOfferDto loanOfferThree = LoanOfferDto.builder().build();
        LoanOfferDto loanOfferFour = LoanOfferDto.builder().build();

        LoanApplicationRequestDto loanDto = LoanApplicationRequestDto.builder().
                amount(new BigDecimal("100000")).birthdate(LocalDate.of(1993, 02, 03))
                .email("String@mail.ru").firstName("Vasiliy").lastName("Moskalev").middleName("Anatolich").passportNumber("111111")
                .passportSeries("1111").term(12).build();

        when(createClienService.createApplication(any())).thenReturn(application);
        when(application.getId()).thenReturn(1L);
        when(feignForOffers.touchOffers(any())).thenReturn(Arrays.asList(loanOfferOne, loanOfferTwo, loanOfferThree, loanOfferFour));

        this.mockMvc.perform(post("/deal/application")
                .contentType(MediaType.APPLICATION_JSON).content(loanApplicationRequestStr))
                .andExpect(status().isOk())
                .andReturn();

        verify(feignForOffers).touchOffers(loanDto);
    }

    @Test
    void selectOneOfOffers() throws Exception {
        File file = ResourceUtils.getFile("classpath:loanOffer.json");
        String loanOffer = new String(Files.readAllBytes(file.toPath()));

        when(dealService.fetchApplFromRepo(any())).thenReturn(application);
        when(feignForOffers.touchOffers(any())).thenReturn(Arrays.asList(loanOfferDto));

        this.mockMvc.perform(put("/deal/offer")
                .contentType(MediaType.APPLICATION_JSON).content(loanOffer))
                .andExpect(status().isOk())
                .andReturn();

        verify(dealService).fetchApplFromRepo(loanOfferDto);
    }

    @Test
    void completionOfRegistrationAndFullCreditCalculation() throws Exception {

        File file = ResourceUtils.getFile("classpath:finishRegistrationRequest.json");
        String finishReg = new String(Files.readAllBytes(file.toPath()));

        LoanOfferDto loanOffer = LoanOfferDto.builder()
                .requestedAmount(BigDecimal.valueOf(100000))
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .term(12)
                .build();

        Client client = new Client().setId(Long.valueOf(1)).setFirst_name("Vasiliy").setLast_name("Moskalev").setMiddle_name("Anatolich").setBirth_date(LocalDate.of(1993, 10, 13)).setPassport("1111").setSeries("111111").setEmail("String@mail.ru").setApplications(new ArrayList<>());
        Application application = new Application().setId(Long.valueOf(1)).setStatus(Status.PREAPPROVAL).setClientApp(client).setClientApp(client).setCredit(mockCredit);
        EmploymentDto employmentDto = new EmploymentDto().setEmploymentStatus(EmploymentStatus.EMPLOYED).setSalary(BigDecimal.valueOf(120000).setScale(0, RoundingMode.HALF_EVEN)).setPosition(Position.MID_MANAGER).setWorkExperienceTotal(15).setWorkExperienceCurrent(4);
        FinishRegistrationRequestDto finishDto = new FinishRegistrationRequestDto().setAccount("account111").setGender(Gender.MALE).setMaritalStatus(MaritalStatus.DIVORCED).setDependentAmount(3).setPassportIssueBrach("УФМС России по Воронежской обл. центрального р-а").setPassportIssueDate(LocalDate.of(2005, 03, 13)).setEmployment(employmentDto);


        ScoringDataDto scoringDataDTO = new ScoringDataDto()
                .setAccount("account111")
                .setAmount(BigDecimal.valueOf(100000))
                .setBirthdate(LocalDate.of(1993, 10, 13))
                .setDependentAmount(3)
                .setEmployment(employmentDto)
                .setFirstName("Vasiliy")
                .setLastName("Moskalev")
                .setMiddleName("Anatolich")
                .setGender(Gender.MALE)
                .setIsInsuranceEnabled(false)
                .setIsSalaryClient(false)
                .setMaritalStatus(MaritalStatus.MARRIED)
                .setPassportIssueBranch("УФМС России по Воронежской обл. центрального р-а")
                .setPassportIssueDate(LocalDate.of(2005, 3, 13))
                .setPassportNumber("111111")
                .setPassportSeries("1111")
                .setTerm(12);

        when(createApplicationService.createScoringDataDto(any(),any())).thenReturn(scoringDataDTO);
        when(feignForScoring.calculateScoringDTO(any())).thenReturn(creditDto);


        this.mockMvc.perform(put("/deal/calculate/1")
                .contentType(MediaType.APPLICATION_JSON).content(finishReg))
                .andExpect(status().isOk())
                .andReturn();

        verify(createApplicationService).createScoringDataDto(application,finishDto);
        verify(feignForScoring).calculateScoringDTO(scoringDataDTO);
    }
}