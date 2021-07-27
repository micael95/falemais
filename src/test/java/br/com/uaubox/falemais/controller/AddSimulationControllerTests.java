package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.Plan;
import br.com.uaubox.falemais.domain.model.TelephoneCharges;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.domain.repository.PlanRepository;
import br.com.uaubox.falemais.domain.repository.TelephoneChargesRepository;
import br.com.uaubox.falemais.domain.usecases.TokenManager;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.SimulationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AddSimulationControllerTests {

    private static final String ADD_SIMULATION_URI = "/api/v1/simulation";
    private static String TOKEN = "";
    private final ModelMapper modelMapper = new ModelMapper();
    private final Faker faker = new Faker();
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private TelephoneChargesRepository telephoneChargesRepository;

    @BeforeEach
    public void beforeEach() {
        String password = faker.internet().password();

        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(bCryptPasswordEncoder.encode(password));

        customerRepository.save(customer);

        UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(customer.getEmail(), password);
        Authentication authentication = authManager.authenticate(loginData);
        TOKEN = tokenManager.generateToken(authentication);
    }

    @Test
    public void shouldReturn400IfValidationReturnsAnError() throws Exception {
        URI uri = new URI(ADD_SIMULATION_URI);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .header("Authorization", "Bearer " + TOKEN)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturn401IfWrongTokenIsProvided() throws Exception {
        URI uri = new URI(ADD_SIMULATION_URI);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .header("Authorization", "Bearer " + "Wrong")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void shouldReturn406IfWrongPlanIsProvided() throws Exception {
        URI uri = new URI(ADD_SIMULATION_URI);
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

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .header("Authorization", "Bearer " + TOKEN)
                .content(objectMapper.writeValueAsString(simulationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    public void shouldReturn201SuccessIfCorrectValuesIsProvided() throws Exception {
        URI uri = new URI(ADD_SIMULATION_URI);
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

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .header("Authorization", "Bearer " + TOKEN)
                .content(objectMapper.writeValueAsString(simulationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
