package br.com.midt.falemais.dto.response;

import br.com.midt.falemais.domain.model.Customer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CustomerResponseTests {

    private final Faker faker = new Faker();
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertCustomerModelToCustomerResponse() {
        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPassword(faker.internet().password());

        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        Assertions.assertEquals(customer.getName(), customerResponse.getName());
        Assertions.assertEquals(customer.getEmail(), customerResponse.getEmail());
    }
}
