package credit.conveer.msDeal.Service.Impl;

import credit.conveer.App;
import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Client.ClientForConveyorOffers;
import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.Client;
import credit.conveer.msDeal.Model.Credit;
import credit.conveer.msDeal.Repository.ApplicationRepository;
import credit.conveer.msDeal.Repository.ClientRepository;
import credit.conveer.msDeal.Repository.CreditRepository;
import credit.conveer.msDeal.Service.CreateApplicationService;
import credit.conveer.msDeal.Service.CreateClienService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CreateClienServiceImpl implements CreateClienService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientForConveyorOffers clientForConveyorOffers;

    Long id = Long.valueOf(1);

    public CreateClienServiceImpl(ClientRepository clientRepository, ApplicationRepository applicationRepository, CreditRepository creditRepository, ClientForConveyorOffers clientForConveyorOffers) {
        this.clientRepository = clientRepository;
        this.applicationRepository = applicationRepository;
        this.creditRepository = creditRepository;
        this.clientForConveyorOffers = clientForConveyorOffers;
    }

    public List<LoanOfferDTO> createClientAndApplication(LoanApplicationRequestDTO dto) {
        log.info("LoanApplicationRequestDTO is "+dto.toString());

        Client client =createClient(dto);
        Application application = createApplication(client);
        Credit credit = createCredot(application,dto);

        application.setCredit(credit);
        client.getApplications().add(application);

        clientRepository.save(client);
        applicationRepository.save(application);
        creditRepository.save(credit);

        Client testClient=clientRepository.findById(Long.valueOf(1)).get();
        //постзапрос через фейн на  /conveyor/offers
        List<LoanOfferDTO> offers = clientForConveyorOffers.touchOffers(dto);
        offers.stream().forEach(e -> e.setApplicationId(application.getId())); //каждому элементу из списка List<LoanOfferDTO> присваивается id созданной заявки
        log.info("offers is "+offers.toString());
        return offers;

    }

    public Application createApplication(Client client){
        Application application = new Application().setClient(client).setId(client.getId()).setCreation_date(LocalDate.now());
        return application;
    }

    public Client createClient(LoanApplicationRequestDTO dto)
    {
        Client client = new Client().setId(id++).setFirst_name(dto.getFirstName())
                .setLast_name(dto.getLastName())
                .setMiddle_name(dto.getMiddleName())
                .setBirth_date(dto.getBirthdate())
                .setPassport(dto.getPassportNumber())
                .setSeries(dto.getPassportSeries())
                .setEmail(dto.getEmail());
        return  client;
    }

    public Credit createCredot(Application application,LoanApplicationRequestDTO dto){
        Credit credit = new Credit().setId(application.getId()).setAmount(dto.getAmount())
                .setTerm(dto.getTerm()).setApplication(application)
                .setIs_insurance_enabled(false)
                .setIs_salary_client(false);
        return credit;
    }
}
