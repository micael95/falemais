package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.exception.EmailAlreadyExistsException;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import br.com.uaubox.falemais.factory.FactoryManager;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
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
            Assertions.fail("exception not thrown");
        } catch (Exception ignored) {}

    }

    @Test()
    public void shouldThrowExceptionIfEmailAlreadyExists() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName(faker.name().name());
        customerRequest.setEmail(faker.internet().emailAddress());
        customerRequest.setPassword(faker.internet().password());
        customerRequest.setPasswordConfirmation(customerRequest.getPassword());
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        this.customerRepository.save(customer);

        try {
            addCustomerService.add(customerRequest);
            Assertions.fail("exception not thrown");
        } catch (Exception ignored) {}

    }


}
