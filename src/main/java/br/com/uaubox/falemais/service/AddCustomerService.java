package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.domain.usecases.AddCustomer;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.dto.response.ErrorResponse;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import br.com.uaubox.falemais.factory.FactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ValidationException;

@Service
public class AddCustomerService extends FactoryManager implements AddCustomer {

    private final CustomerRepository customerRepository;

    @Autowired
    public AddCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse add(CustomerRequest customerRequest) throws InvalidPasswordConfirmationException {
        Customer customer = getObjectFromRequest(customerRequest, Customer.class);

        if (!customer.getPassword().equals(customerRequest.getPasswordConfirmation()))
            throw new InvalidPasswordConfirmationException();
        customerRepository.save(customer);
        return getResponseFromObject(customer, CustomerResponse.class);
    }
}
