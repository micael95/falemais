package br.com.uaubox.falemais.domain.usecases;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.SimulationResponse;

public interface AddSimulation {
    SimulationResponse add(SimulationRequest simulationRequest, Customer customer) throws Exception;
}
