package credit.conveer.msDeal.service.Impl;

import credit.conveer.ms1.dto.LoanApplicationRequestDto;
import credit.conveer.ms1.dto.LoanOfferDto;
import credit.conveer.msDeal.сlient.ClientForConveyorOffers;
import credit.conveer.msDeal.model.Application;
import credit.conveer.msDeal.model.Client;
import credit.conveer.msDeal.model.Credit;
import credit.conveer.msDeal.model.Enum.Status;
import credit.conveer.msDeal.repository.ApplicationRepository;
import credit.conveer.msDeal.repository.ClientRepository;
import credit.conveer.msDeal.repository.CreditRepository;
import credit.conveer.msDeal.service.CreateClienService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateClienServiceImpl implements CreateClienService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientForConveyorOffers clientForConveyorOffers;

    @Transactional
    public List<LoanOfferDto> createClientAndApplication(LoanApplicationRequestDto dto) {
        log.info("enter LoanApplicationRequestDTO is ={} ",dto);

        Client client = createClient(dto);
        client = clientRepository.save(client);
        Application application = createApplication(client);
        application = applicationRepository.save(application);
        Credit credit = createCredot(application, dto);


        client.addApplication(application);
        credit = creditRepository.save(credit);
        application.setCredit(credit);

        clientRepository.save(client);
        application = applicationRepository.save(application);

        //постзапрос через фейн на  /conveyor/offers
        List<LoanOfferDto> offers = clientForConveyorOffers.touchOffers(dto);
        Application finalApplication = application;
        offers.stream().forEach(e -> e.setApplicationId(finalApplication.getId())); //каждому элементу из списка List<LoanOfferDTO> присваивается id созданной заявки
        log.info("return list of offers is ={} ", offers);
        return offers;

    }

    public Application createApplication(Client client) {
        log.info("enter object client  is ={} ", client);
        Application application = new Application().setClientApp(client).setCreation_date(LocalDate.now()).setStatus(Status.PREAPPROVAL);
        log.info("return object application  is ={} ", application);
        return application;
    }

    public Client createClient(LoanApplicationRequestDto dto) {
        log.info("enter LoanApplicationRequestDto  is ={} ", dto);
        Client client = new Client().setFirst_name(dto.getFirstName())
                .setLast_name(dto.getLastName())
                .setMiddle_name(dto.getMiddleName())
                .setBirth_date(dto.getBirthdate())
                .setPassport(dto.getPassportNumber())
                .setSeries(dto.getPassportSeries())
                .setEmail(dto.getEmail())
                .setApplications(new ArrayList<>());
        log.info("return object client  is ={} ", client);
        return client;
    }

    public Credit createCredot(Application application, LoanApplicationRequestDto dto) {
        log.info("enter object application  is ={} , and  LoanApplicationRequestDto ={}",application, dto);
        Credit credit = new Credit().setAmount(dto.getAmount())
                .setTerm(dto.getTerm()).setApplication(application)
                .setIs_insurance_enabled(false)
                .setIs_salary_client(false);
        log.info("return object credit  is ={} ", credit);
        return credit;
    }
}
