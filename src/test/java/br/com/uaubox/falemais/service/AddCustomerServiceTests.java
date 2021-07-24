package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AddCustomerServiceTests {

    private final Faker faker = new Faker();
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private CustomerRepository customerRepository;

    private AddCustomerService addCustomerService;

    @BeforeEach
    private void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.addCustomerService = new AddCustomerService(customerRepository);
    }


    @Test()
    public void shouldThrowExceptionIfPasswordConfirmationIsInvalid() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName(faker.name().name());
        customerRequest.setEmail(faker.internet().emailAddress());
        customerRequest.setPassword(faker.internet().password());
        customerRequest.setPasswordConfirmation("invalid_password");

        try {
            addCustomerService.add(customerRequest);
            Assert.fail("exception not throed");
        } catch (InvalidPasswordConfirmationException e) {}

    }


}
