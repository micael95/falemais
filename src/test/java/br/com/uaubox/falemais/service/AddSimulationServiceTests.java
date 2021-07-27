package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.Plan;
import br.com.uaubox.falemais.domain.model.TelephoneCharges;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.domain.repository.PlanRepository;
import br.com.uaubox.falemais.domain.repository.SimulationRepository;
import br.com.uaubox.falemais.domain.repository.TelephoneChargesRepository;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.SimulationResponse;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddSimulationServiceTests {


    @Autowired
    private TelephoneChargesRepository telephoneChargesRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final Faker faker = new Faker();
    private final ModelMapper modelMapper = new ModelMapper();

    private AddSimulationService addSimulationService;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.addSimulationService = new AddSimulationService(simulationRepository, telephoneChargesRepository, planRepository);
    }

    @Test
    public void shouldReturnAnExceptionIfPlanDoesNotExists() {
        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPassword(faker.internet().password());

        customerRepository.save(customer);

        TelephoneCharges telephoneCharges = new TelephoneCharges();
        telephoneCharges.setOrigin(Integer.parseInt(faker.phoneNumber().subscriberNumber(2)));
        telephoneCharges.setDestination(Integer.parseInt(faker.phoneNumber().subscriberNumber(2)));
        telephoneCharges.setPerMinuteRate(new BigDecimal(faker.number().digits(2)));
        telephoneChargesRepository.save(telephoneCharges);

        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setOrigin(telephoneCharges.getOrigin());
        simulationRequest.setDestination(telephoneCharges.getDestination());
        simulationRequest.setTimeInMinutes(Integer.valueOf(faker.number().digits(2)));
        simulationRequest.setPlanId("invalid_plan_id");

        try {
            this.addSimulationService.add(simulationRequest,customer);
            Assertions.fail("exception not thrown");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void shouldCalculateSimulationWithCorrectValues() throws Exception {

        List<Plan> planList = planRepository.findAll();

        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPassword(faker.internet().password());

        customerRepository.save(customer);

        TelephoneCharges telephoneCharges = new TelephoneCharges();
        telephoneCharges.setOrigin(Integer.parseInt(faker.phoneNumber().subscriberNumber(2)));
        telephoneCharges.setDestination(Integer.parseInt(faker.phoneNumber().subscriberNumber(2)));
        telephoneCharges.setPerMinuteRate(new BigDecimal(faker.number().digits(2)));
        telephoneChargesRepository.save(telephoneCharges);

        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setOrigin(telephoneCharges.getOrigin());
        simulationRequest.setDestination(telephoneCharges.getDestination());
        simulationRequest.setTimeInMinutes(Integer.valueOf(faker.number().digits(2)));
        Plan selectedPlan = planList.get(0);
        simulationRequest.setPlanId(selectedPlan.getPlanId());

        Integer timeInMinutesRequest = simulationRequest.getTimeInMinutes();
        Integer timeInMinutesPlan = selectedPlan.getMinutes();

        int timeInMinutes = timeInMinutesPlan - timeInMinutesRequest;
        double price = 0.00;
        if (timeInMinutes < 0) {
            timeInMinutes = -timeInMinutes;
            double perMinuteRate = telephoneCharges.getPerMinuteRate().doubleValue();
            price = timeInMinutes * (perMinuteRate + (perMinuteRate * (selectedPlan.getSurplusPercentage().doubleValue() / 100)));
        }

        SimulationResponse simulationResponse = this.addSimulationService.add(simulationRequest,customer);
        Assertions.assertEquals(timeInMinutesRequest, simulationResponse.getTimeInMinutes());
        Assertions.assertEquals(price, simulationResponse.getPrice().doubleValue());

    }
}
