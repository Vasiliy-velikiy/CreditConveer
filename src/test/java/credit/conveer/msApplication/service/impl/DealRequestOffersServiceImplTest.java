package credit.conveer.msApplication.service.impl;

import credit.conveer.msApplication.client.ClientForDealOffersService;
import credit.conveer.msApplication.dto.LoanOfferDto;
import credit.conveer.msApplication.service.DealRequestOffersService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@RunWith(SpringRunner.class)
class DealRequestOffersServiceImplTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientForDealOffersService clientForDealOffersService;
    @MockBean
    private DealRequestOffersService dealRequestOffersService;

    @Test
    void selectOneOfOffers() throws Exception {
        File file = ResourceUtils.getFile("classpath:loanOffer.json");
        String loanOffer = new String(Files.readAllBytes(file.toPath()));
        LoanOfferDto loanOfferDTOOne=LoanOfferDto.builder().applicationId(Long.valueOf(0)).requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(new BigDecimal(115476.7420).setScale(4, RoundingMode.HALF_EVEN)).term(12).monthlyPayment(new BigDecimal(8789.7285).setScale(4, RoundingMode.HALF_EVEN))
                .rate(BigDecimal.valueOf(0.10)).isInsuranceEnabled(false).isSalaryClient(false).build();

        this.mockMvc.perform(put("/deal/offer")
                .contentType(MediaType.APPLICATION_JSON).content(loanOffer))
                .andExpect(status().isOk())
                .andReturn();

        verify(clientForDealOffersService).calculateOffers(loanOfferDTOOne);
        verify(dealRequestOffersService).selectOneOfOffers(loanOfferDTOOne);
    }
}