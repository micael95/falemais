package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.domain.usecases.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import java.net.URI;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LoadPlansControllerTests {
    private static final String PLANS_URI = "/api/v1/simulation/plans";
    private static String TOKEN = "";
    private final ModelMapper modelMapper = new ModelMapper();
    private final Faker faker = new Faker();

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
    public void shouldReturn401IfWrongTokenIsProvided() throws Exception {
        URI uri = new URI(PLANS_URI);
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", "Bearer " + "Wrong")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void shouldReturn200SuccessWithListOfPlans() throws Exception {
        URI uri = new URI(PLANS_URI);
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
