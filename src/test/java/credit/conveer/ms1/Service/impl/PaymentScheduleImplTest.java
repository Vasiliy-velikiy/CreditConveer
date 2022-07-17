package credit.conveer.ms1.Service.impl;

import credit.conveer.ms1.Dto.EmploymentDTO;
import credit.conveer.ms1.Dto.PaymentScheduleElement;
import credit.conveer.ms1.Dto.ScoringDataDTO;
import credit.conveer.ms1.Service.PaymentSchedule;
import credit.conveer.msDeal.Model.Enum.EmploymentStatus;
import credit.conveer.msDeal.Model.Enum.Gender;
import credit.conveer.msDeal.Model.Enum.MaritalStatus;
import credit.conveer.msDeal.Model.Enum.Position;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PaymentScheduleImplTest {

    @Autowired
    private PaymentSchedule paymentSchedule;


    private enum employmentStatus {
        SELF_EMPLOYED;
    }

    private enum position {
        TOP_MANAGER;
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

    @Test
    void scheduleDebtPayment() {
        List<PaymentScheduleElement> result= paymentSchedule.schedule(scoringDataDTO);
        PaymentScheduleElement elemRes=result.get(2);
        BigDecimal elemExpec= BigDecimal.valueOf(9418.8395).setScale(4, RoundingMode.HALF_EVEN);
        assertEquals(elemExpec,elemRes.getDebtPayment());
    }

    @Test
    void scheduleInterestPayment() {
        List<PaymentScheduleElement> result= paymentSchedule.schedule(scoringDataDTO);
        PaymentScheduleElement elemRes=result.get(2);
        BigDecimal elemExpec= BigDecimal.valueOf(8.5).setScale(1, RoundingMode.HALF_EVEN);
        assertEquals(elemExpec,elemRes.getInterestPayment());
    }

    @Test
    void scheduleTotalPayment() {
        List<PaymentScheduleElement> result= paymentSchedule.schedule(scoringDataDTO);
        PaymentScheduleElement elemRes=result.get(2);
        BigDecimal elemExpec= BigDecimal.valueOf(113026.0740).setScale(4, RoundingMode.HALF_EVEN);
        assertEquals(elemExpec,elemRes.getTotalPayment());
    }
}