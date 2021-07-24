package br.com.uaubox.falemais.dto.response;

import br.com.uaubox.falemais.domain.model.Customer;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CustomerResponseTest {

    private Faker faker = new Faker();
    private ModelMapper modelMapper = new ModelMapper();

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
        Assert.assertEquals(customer.getName(), customerResponse.getName());
        Assert.assertEquals(customer.getEmail(), customerResponse.getEmail());
        Assert.assertEquals(customer.getPassword(), customerResponse.getPassword());
    }
}
