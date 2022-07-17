package credit.conveer.ms1.Service.impl;

import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.ms1.Service.PrescoringOffers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PrescoringOffersImplTest {

    @Autowired
    private  PrescoringOffers prescoringOffers;
    private   LoanApplicationRequestDTO loanDto= LoanApplicationRequestDTO.builder().
            amount(new BigDecimal("100000")).birthdate(LocalDate.of(1993, 02, 03))
            .email("test@mail.ru").firstName("Ivan").lastName("Ivanov").middleName("Ivanivich").passportNumber("123456")
            .passportSeries("1234").term(12).build();

    @Test
    void getMonthlyPayment() {
        BigDecimal result=prescoringOffers.getMonthlyPayment(loanDto.getAmount(), BigDecimal.valueOf(0.10),12,false);
        BigDecimal expected= BigDecimal.valueOf(8789.7285).setScale(4, RoundingMode.HALF_EVEN);
        assertEquals(expected,result);
    }

    @Test
    void getInsurance() {
        BigDecimal result=prescoringOffers.getInsurance(loanDto.getAmount());
        BigDecimal expected= BigDecimal.valueOf(10000).setScale(4, RoundingMode.HALF_EVEN);
        assertEquals(expected,result);
    }

    @Test
    void getTotalAmount() {
       BigDecimal result=  prescoringOffers.getTotalAmount(loanDto.getAmount(), BigDecimal.valueOf(0.10),loanDto.getTerm(),false);
        System.out.println(result);
        BigDecimal expected= BigDecimal.valueOf(105476.7420).setScale(4, RoundingMode.HALF_EVEN);
        assertEquals(expected,result);
    }

    @Test
    void getPrescoringOffers() {
        List<LoanOfferDTO> expected = new ArrayList<>();

        LoanOfferDTO loanOfferDTOOne=LoanOfferDTO.builder().applicationId(Long.valueOf(0)).requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(new BigDecimal(115476.7420).setScale(4, RoundingMode.HALF_EVEN)).term(12).monthlyPayment(new BigDecimal(8789.7285).setScale(4, RoundingMode.HALF_EVEN))
                .rate(BigDecimal.valueOf(0.10)).isInsuranceEnabled(false).isSalaryClient(false).build();

        LoanOfferDTO loanOfferDTOTwo=LoanOfferDTO.builder().applicationId(Long.valueOf(1)).requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(new BigDecimal(126024.4156).setScale(4, RoundingMode.HALF_EVEN)).term(12)
                .monthlyPayment(new BigDecimal(9668.7013).setScale(4, RoundingMode.HALF_EVEN))
                .rate(BigDecimal.valueOf(0.10)).isInsuranceEnabled(true).isSalaryClient(false).build();

        LoanOfferDTO loanOfferDTOThree=LoanOfferDTO.builder().applicationId(Long.valueOf(2)).requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(new BigDecimal(126024.4156).setScale(4, RoundingMode.HALF_EVEN)).term(12)
                .monthlyPayment(new BigDecimal(9668.7013).setScale(4, RoundingMode.HALF_EVEN))
                .rate(BigDecimal.valueOf(0.10)).isInsuranceEnabled(true).isSalaryClient(true).build();


        LoanOfferDTO loanOfferDTOFour=LoanOfferDTO.builder().applicationId(Long.valueOf(3)).requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(new BigDecimal(115476.7420).setScale(4, RoundingMode.HALF_EVEN)).term(12)
                .monthlyPayment(new BigDecimal(8789.7285).setScale(4, RoundingMode.HALF_EVEN))
                .rate(BigDecimal.valueOf(0.10)).isInsuranceEnabled(false).isSalaryClient(true).build();


                expected.add(loanOfferDTOOne);
                expected.add(loanOfferDTOTwo);
                expected.add(loanOfferDTOThree);
                expected.add(loanOfferDTOFour);

        List<LoanOfferDTO> result = prescoringOffers.getPrescoringOffers(loanDto);
        assertEquals(expected,result);

    }
}