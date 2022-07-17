package credit.conveer.msDeal.Service.Impl;

import credit.conveer.App;
import credit.conveer.ms1.Dto.LoanApplicationRequestDTO;
import credit.conveer.ms1.Dto.LoanOfferDTO;
import credit.conveer.msDeal.Client.ClientForConveyorOffers;
import credit.conveer.msDeal.Dto.FinishRegistrationRequestDTO;
import credit.conveer.msDeal.Model.Application;
import credit.conveer.msDeal.Model.Client;
import credit.conveer.msDeal.Model.Credit;
import credit.conveer.msDeal.Model.Enum.Status;
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
public class CreateClienServiceImpl implements CreateClienService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientForConveyorOffers clientForConveyorOffers;

    Long idForClient = Long.valueOf(1);
    Long idForApplication = Long.valueOf(1);
    Long idForCredit = Long.valueOf(1);

    public CreateClienServiceImpl(ClientRepository clientRepository, ApplicationRepository applicationRepository, CreditRepository creditRepository, ClientForConveyorOffers clientForConveyorOffers) {
        this.clientRepository = clientRepository;
        this.applicationRepository = applicationRepository;
        this.creditRepository = creditRepository;
        this.clientForConveyorOffers = clientForConveyorOffers;
    }

    @Transactional
    public List<LoanOfferDTO> createClientAndApplication(LoanApplicationRequestDTO dto) {
        log.info("LoanApplicationRequestDTO is " + dto.toString());

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
        List<LoanOfferDTO> offers = clientForConveyorOffers.touchOffers(dto);
        offers.stream().forEach(e -> e.setApplicationId(idForApplication)); //каждому элементу из списка List<LoanOfferDTO> присваивается id созданной заявки
        log.info("offers is " + offers.toString());
        return offers;

    }

    public Application createApplication(Client client) {
        Application application = new Application().setClientApp(client).setId(idForApplication++).setCreation_date(LocalDate.now()).setStatus(Status.PREAPPROVAL);
        return application;
    }

    public Client createClient(LoanApplicationRequestDTO dto) {
        Client client = new Client().setId(idForClient++).setFirst_name(dto.getFirstName())
                .setLast_name(dto.getLastName())
                .setMiddle_name(dto.getMiddleName())
                .setBirth_date(dto.getBirthdate())
                .setPassport(dto.getPassportNumber())
                .setSeries(dto.getPassportSeries())
                .setEmail(dto.getEmail())
                .setApplications(new ArrayList<>());
        return client;
    }

    public Credit createCredot(Application application, LoanApplicationRequestDTO dto) {
        Credit credit = new Credit().setId(idForCredit++).setAmount(dto.getAmount())
                .setTerm(dto.getTerm()).setApplication(application)
                .setIs_insurance_enabled(false)
                .setIs_salary_client(false);
        return credit;
    }
}
