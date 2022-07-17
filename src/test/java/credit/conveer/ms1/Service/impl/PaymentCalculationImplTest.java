package credit.conveer.ms1.Service.impl;

import credit.conveer.ms1.Dto.EmploymentDTO;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.ms1.Service.PaymentCalculation;
import credit.conveer.msDeal.Model.Enum.EmploymentStatus;
import credit.conveer.msDeal.Model.Enum.Gender;
import credit.conveer.msDeal.Model.Enum.MaritalStatus;
import credit.conveer.msDeal.Model.Enum.Position;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PaymentCalculationImplTest {

    @Autowired
    private PaymentCalculation paymentCalculation;

    private enum maritalStatus {
        MARRIED;
    }

    private enum employmentStatus {
        SELF_EMPLOYED;
    }

    private enum position {
        TOP_MANAGER;
    }

    private enum gender {
        MALE;
    }

    private EmploymentDTO employmentDTO = new EmploymentDTO()
            .setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED)
            .setEmployerINN("324245")
            .setSalary(new BigDecimal("400000"))
            .setPosition(Position.TOP_MANAGER)
            .setWorkExperienceTotal(19)
            .setWorkExperienceCurrent(14);

    //insurance enable
    private ScoringDataDTO scoringDataDTO = new ScoringDataDTO()
            .setAmount(new BigDecimal("100000"))
            .setTerm(12)
            .setFirstName("Ivan")
            .setLastName("Ivanov")
            .setMiddleName("Ivanovich")
            .setGender(Gender.MALE)
            .setBirthdate(LocalDate.of(1993, 02, 03))
            .setPassportIssueDate(LocalDate.of(2005, 3, 13))
            .setPassportIssueBranch("УФМС России по Воронежской обл. центрального р-а")
            .setMaritalStatus(MaritalStatus.MARRIED)
            .setDependentAmount(3)
            .setEmployment(employmentDTO)
            .setAccount("account")
            .setIsInsuranceEnabled(true)
            .setIsSalaryClient(true);

    //insurace disable
    private ScoringDataDTO scoringDataDTOWithoutInsurance = new ScoringDataDTO()
            .setAmount(new BigDecimal("100000"))
            .setTerm(12)
            .setFirstName("Alexander")
            .setLastName("Ivanov")
            .setMiddleName("Ivanov")
            .setGender(Gender.MALE)
            .setBirthdate(LocalDate.of(1992, 03, 03))
            .setPassportIssueDate(LocalDate.of(2005, 3, 13))
            .setPassportIssueBranch("УФМС России по Воронежской обл. центрального р-а")
            .setMaritalStatus(MaritalStatus.MARRIED)
            .setDependentAmount(2)
            .setEmployment(employmentDTO)
            .setAccount("account")
            .setIsInsuranceEnabled(false)
            .setIsSalaryClient(true);


    @Test
    void getMonthlyPaymentWithoutInsurance() {
        System.out.println(scoringDataDTOWithoutInsurance);
        BigDecimal result = paymentCalculation.getMonthlyPayment(scoringDataDTOWithoutInsurance);
        BigDecimal expected = new BigDecimal("8562.5813");
        assertEquals(expected,result);
    }

    @Test
    void getMonthlyPaymentWithInsurance() {
        BigDecimal result = paymentCalculation.getMonthlyPayment(scoringDataDTO);
        BigDecimal expected = new BigDecimal("9418.8395");
        assertEquals(expected,result);
    }

    @Test
    void getInsurance() {
        BigDecimal result = paymentCalculation.getInsurance(scoringDataDTO);
        BigDecimal expected = new BigDecimal("10000");
        assertEquals(expected,result);
    }

    @Test
    void getTotalAmountWithoutInsurance() {
        BigDecimal result = paymentCalculation.getTotalAmount(scoringDataDTOWithoutInsurance);
        BigDecimal expected = new BigDecimal("102750.9756");
        assertEquals(expected,result);
    }

    @Test
    void getTotalAmountWithInsurance() {
        BigDecimal result = paymentCalculation.getTotalAmount(scoringDataDTO);
        BigDecimal expected = new BigDecimal("113026.0740");
        assertEquals(expected,result);
    }

    @Test
    void getPskWithoutInsurance() {
        BigDecimal result = paymentCalculation.getPsk(scoringDataDTOWithoutInsurance);
        BigDecimal expected = new BigDecimal("2.7510");
        assertEquals(expected,result);
    }

    @Test
    void getPskWithInsurance() {
        BigDecimal result = paymentCalculation.getPsk(scoringDataDTO);
        BigDecimal expected = new BigDecimal("2.7510");
        assertEquals(expected,result);
    }

    @Test
    void getRate() {
        BigDecimal result = paymentCalculation.getRate(scoringDataDTO);
        BigDecimal expected = new BigDecimal("0.05");
        assertEquals(expected,result);
    }
}