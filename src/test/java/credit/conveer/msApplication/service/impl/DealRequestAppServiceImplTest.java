package credit.conveer.msApplication.service.impl;

import credit.conveer.msApplication.client.ClientForDealApplicationService;
import credit.conveer.msApplication.dto.LoanApplicationRequestDto;
import credit.conveer.msApplication.dto.LoanOfferDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@RunWith(SpringRunner.class)
class DealRequestAppServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealRequestAppServiceImpl dealRequestAppService;
    @MockBean
    private ClientForDealApplicationService clientForDealApplicationService;

    @Test
    void prescoringAndEvaluateRules() throws Exception {
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

        when(dealRequestAppService.prescoringAndEvaluateRules(any())).thenReturn(Arrays.asList(loanOfferOne, loanOfferTwo, loanOfferThree, loanOfferFour));
        when(clientForDealApplicationService.calculateOffers(any())).thenReturn(Arrays.asList(loanOfferOne, loanOfferTwo, loanOfferThree, loanOfferFour));


        this.mockMvc.perform(post("/deal/application")
                .contentType(MediaType.APPLICATION_JSON).content(loanApplicationRequestStr))
                .andExpect(status().isOk())
                .andReturn();


        verify(clientForDealApplicationService).calculateOffers(loanDto);
        verify(clientForDealApplicationService).calculateOffers(loanDto);
    }
}