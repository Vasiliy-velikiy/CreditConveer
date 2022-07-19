package credit.conveer.msDeal.service.Impl;

import credit.conveer.ms1.dto.LoanApplicationRequestDto;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.Client;
import credit.conveer.msDeal.model.Credit;
import credit.conveer.msDeal.model.Enum.Status;
import credit.conveer.msDeal.service.CreateClienService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
class CreateClienServiceImplTest {

    @Autowired
    private CreateClienService createClienService;

    private LoanApplicationRequestDto loanDto = LoanApplicationRequestDto.builder().
            amount(new BigDecimal("100000")).birthdate(LocalDate.of(1993, 02, 03))
            .email("String@mail.ru").firstName("Vasiliy").lastName("Moskalev").middleName("Anatolich").passportNumber("111111")
            .passportSeries("1111").term(12).build();

    private Client client = new Client().setId(Long.valueOf(1)).setFirst_name("Vasiliy")
            .setLast_name("Moskalev")
            .setMiddle_name("Anatolich")
            .setBirth_date(LocalDate.of(1993, 10, 13))
            .setPassport("111111")
            .setSeries("1111")
            .setEmail("String@mail.ru")
            .setApplications(new ArrayList<>());

    private Application expectedAppl = new Application().setId(Long.valueOf(1)).setStatus(Status.PREAPPROVAL).setClientApp(client);

    @Test
    void createApplication() {
        Application actual = createClienService.createApplication(client);
        assertEquals(expectedAppl.getClientApp().getFirst_name(), actual.getClientApp().getFirst_name());
        assertEquals(expectedAppl.getClientApp().getPassport(), actual.getClientApp().getPassport());
        assertEquals(expectedAppl.getStatus(), actual.getStatus());
    }

    @Test
    void createClient() {
        Client actual = createClienService.createClient(loanDto);
        assertEquals(client.getPassport(), actual.getPassport());
        assertEquals(client.getFirst_name(), actual.getFirst_name());
        assertEquals(client.getEmail(), actual.getEmail());
    }

    @Test
    void createCredot() {
        Credit expected = new Credit().setId(Long.valueOf(1)).setAmount(BigDecimal.valueOf(100000).setScale(0, RoundingMode.HALF_EVEN)).setTerm(12);
        Credit actual = createClienService.createCredot(expectedAppl, loanDto);
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTerm(), actual.getTerm());
    }
}