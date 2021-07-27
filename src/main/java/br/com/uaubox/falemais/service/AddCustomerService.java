package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.domain.usecases.AddCustomer;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.exception.EmailAlreadyExistsException;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import br.com.uaubox.falemais.factory.FactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddCustomerService extends FactoryManager implements AddCustomer {

    private final CustomerRepository customerRepository;

    @Autowired
    public AddCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerResponse add(CustomerRequest customerRequest) throws InvalidPasswordConfirmationException, EmailAlreadyExistsException {
        Customer customer = getObjectFromRequest(customerRequest, Customer.class);

        if (!customer.getPassword().equals(customerRequest.getPasswordConfirmation()))
            throw new InvalidPasswordConfirmationException();

        Optional<Customer> hasCustomerInDb = customerRepository.findByEmail(customerRequest.getEmail());
        if (hasCustomerInDb.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer);
        return getResponseFromObject(customer, CustomerResponse.class);
    }
}
