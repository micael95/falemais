package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.repository.CustomerRepository;
import br.com.uaubox.falemais.domain.usecases.AddCustomer;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.factory.FactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddCustomerService extends FactoryManager implements AddCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse add(CustomerRequest customerRequest) {
        Customer customer = getObjectFromRequest(customerRequest, Customer.class);
        customerRepository.save(customer);
        return getResponseFromObject(customer, CustomerResponse.class);
    }
}
