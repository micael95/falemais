package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.dto.request.TokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class LoginControllerTests {

    private static final String LOGIN_URI = "/oauth/token";
    private final Faker faker = new Faker();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldReturn400IfValidationReturnsAnError() throws Exception {
        URI uri = new URI(LOGIN_URI);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturn401IfInvalidCredentialsIsProvided() throws Exception {
        URI uri = new URI(LOGIN_URI);

        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPassword(faker.internet().password());

        customerRepository.save(customer);

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setEmail(customer.getEmail());
        tokenRequest.setPassword("invalid_password");

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(tokenRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void shouldReturn200IfCorrectCredentialsIsProvided() throws Exception {
        URI uri = new URI(LOGIN_URI);
        String password = faker.internet().password();

        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(bCryptPasswordEncoder.encode(password));

        customerRepository.save(customer);

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setEmail(customer.getEmail());
        tokenRequest.setPassword(password);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(tokenRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
