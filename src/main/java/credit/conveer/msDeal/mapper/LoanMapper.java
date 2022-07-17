package credit.conveer.msDeal.mapper;

import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.LoanOffer;
import credit.conveer.msDeal.Repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanMapper {

    private final ApplicationRepository applicationRepository;

    public LoanMapper(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public LoanOffer toEntity(LoanOfferDTO dto) {
        Optional<Application> optional = applicationRepository.findById(dto.getApplicationId());
        Application application = optional.get();

        LoanOffer entity = new LoanOffer()
                .setRequestedAmount(dto.getRequestedAmount())
                .setTerm(dto.getTerm())
                .setMonthlyPayment(dto.getMonthlyPayment())
                .setRate(dto.getRate())
                .setIsInsuranceEnabled(dto.getIsInsuranceEnabled())
                .setIsSalaryClient(dto.getIsSalaryClient())
                .setApplicationWithLoan(application);
        return entity;
    }


    public LoanOfferDTO toDto(LoanOffer entity) {
        LoanOfferDTO loanOfferDTO= LoanOfferDTO.builder().requestedAmount(entity.getRequestedAmount()).term(entity.getTerm()).monthlyPayment(entity.getMonthlyPayment())
                .rate(entity.getRate()).isInsuranceEnabled(entity.getIsInsuranceEnabled()).isSalaryClient(entity.getIsSalaryClient()).applicationId(entity.getApplicationWithLoan().getId()).build();
        return loanOfferDTO;
    }
}
