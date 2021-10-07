package br.com.midt.falemais.domain.usecases;

import br.com.midt.falemais.exception.InvalidPasswordConfirmationException;
import br.com.midt.falemais.dto.request.CustomerRequest;
import br.com.midt.falemais.dto.response.CustomerResponse;
import br.com.midt.falemais.exception.EmailAlreadyExistsException;

public interface AddCustomer {
    CustomerResponse add(CustomerRequest customerRequest) throws InvalidPasswordConfirmationException, EmailAlreadyExistsException;
}
