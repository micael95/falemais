package br.com.uaubox.falemais.dto.request;

import br.com.uaubox.falemais.domain.model.Customer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CustomerRequestTests {
    
    private final Faker faker = new Faker();
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertCustomerRequestToCustomerModel() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName(faker.name().name());
        customerRequest.setEmail(faker.internet().emailAddress());
        customerRequest.setPassword(faker.internet().password());

        Customer customer = modelMapper.map(customerRequest, Customer.class);
        Assertions.assertEquals(customer.getName(), customerRequest.getName());
        Assertions.assertEquals(customer.getEmail(), customerRequest.getEmail());
        Assertions.assertEquals(customer.getPassword(), customerRequest.getPassword());
    }


}
