package credit.conveer.msDeal.service.Impl;

import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.model.*;
import credit.conveer.msDeal.model.Enum.ChangeType;
import credit.conveer.msDeal.model.Enum.Status;
import credit.conveer.msDeal.service.DealService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
class DealServiceImplTest {

    @Autowired
    private DealService dealService;

    private LoanOfferDto dto = LoanOfferDto.builder().applicationId(Long.valueOf(1)).requestedAmount(BigDecimal.valueOf(100000)).totalAmount(BigDecimal.valueOf(115476.7420).setScale(4, RoundingMode.HALF_EVEN)).term(12)
            .monthlyPayment(BigDecimal.valueOf(8789.7285).setScale(4, RoundingMode.HALF_EVEN)).rate(BigDecimal.valueOf(0.10).setScale(2, RoundingMode.HALF_EVEN)).isInsuranceEnabled(false).isSalaryClient(false).build();

    Credit mockCredit = Mockito.mock(Credit.class);
    Client mockClient = Mockito.mock(Client.class);
    LoanOffer mockLoanOffer = Mockito.mock(LoanOffer.class);


    private Application application = new Application().setId(Long.valueOf(1)).setClientApp(mockClient).setCredit(mockCredit).setStatus(Status.APPROVED)
            .setCreation_date(LocalDate.now())
            .setAppliedOffer(mockLoanOffer).setSign_date(null).setSes_code(null).setStatus_history(null);

    @Test
    void createApplStatusHistory() {
        ApplicationStatusHistory expected = new ApplicationStatusHistory().setId(Long.valueOf(1))
                .setStatus(Status.APPROVED).setTime(LocalDateTime.now()).setChangeType(ChangeType.CHANGED)
                .setApplication(application);

        ApplicationStatusHistory actual = dealService.createApplStatusHistory(application);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getChangeType(), actual.getChangeType());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void fetchApplFromRepo() {
        Application actual = dealService.fetchApplFromRepo(dto);
        Application expected = application;
        System.out.println(actual.toString());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCreation_date(), actual.getCreation_date());
    }
}