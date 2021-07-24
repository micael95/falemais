package br.com.uaubox.falemais.domain.usecases;

import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;

public interface AddCustomer {
    CustomerResponse add(CustomerRequest customerRequest);
}
