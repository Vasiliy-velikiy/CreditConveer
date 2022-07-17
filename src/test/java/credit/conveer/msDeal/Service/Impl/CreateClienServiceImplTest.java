package credit.conveer.msDeal.Service.Impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.msDeal.Client.ClientForConveyorOffers;
import credit.conveer.msDeal.MockConfig.MockClass;


import credit.conveer.msDeal.MockConfig.WireMockConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;


@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
class CreateClienServiceImplTest {


    @Autowired
    private ClientForConveyorOffers client;

    @Autowired
    private WireMockServer wireMockServer;


    @BeforeEach
    void setUp() throws IOException {
        MockClass.setupMockResponse(wireMockServer);
    }

    private LoanApplicationRequestDTO loanDto = LoanApplicationRequestDTO.builder().
            amount(new BigDecimal("100000")).birthdate(LocalDate.of(1993, 02, 03))
            .email("test@mail.ru").firstName("Ivan").lastName("Ivanov").middleName("Ivanivich").passportNumber("123456")
            .passportSeries("1234").term(12).build();


    @Test
    void createClientAndApplication() {
       // createClienService.createClientAndApplication(loanDto);
        client.touchOffers(loanDto);
        Assert.isTrue((client.touchOffers(loanDto).isEmpty()),"true");
    }
}