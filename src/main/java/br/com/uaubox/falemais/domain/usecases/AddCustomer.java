package br.com.uaubox.falemais.domain.usecases;

import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.exception.EmailAlreadyExistsException;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;

public interface AddCustomer {
    CustomerResponse add(CustomerRequest customerRequest) throws InvalidPasswordConfirmationException, EmailAlreadyExistsException;
}
