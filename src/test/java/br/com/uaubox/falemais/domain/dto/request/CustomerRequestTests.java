package br.com.uaubox.falemais.domain.dto.request;

import br.com.uaubox.falemais.domain.model.Customer;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CustomerRequestTests {
    
    private Faker faker = new Faker();
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertCustomerModelToCustomerRequest() {
        Customer customer = new Customer();
        customer.setName(faker.name().name());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPassword(faker.internet().password());

        CustomerRequest customerRequest = modelMapper.map(customer, CustomerRequest.class);
        Assert.assertEquals(customer.getName(), customerRequest.getName());
        Assert.assertEquals(customer.getEmail(), customerRequest.getEmail());
        Assert.assertEquals(customer.getPassword(), customerRequest.getPassword());
    }

    @Test
    public void shouldConvertCustomerRequestToCustomerModel() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName(faker.name().name());
        customerRequest.setEmail(faker.internet().emailAddress());
        customerRequest.setPassword(faker.internet().password());

        Customer customer = modelMapper.map(customerRequest, Customer.class);
        Assert.assertEquals(customer.getName(), customerRequest.getName());
        Assert.assertEquals(customer.getEmail(), customerRequest.getEmail());
        Assert.assertEquals(customer.getPassword(), customerRequest.getPassword());
    }


}
