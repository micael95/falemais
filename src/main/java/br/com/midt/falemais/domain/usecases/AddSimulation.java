package br.com.midt.falemais.domain.usecases;

import br.com.midt.falemais.domain.model.Customer;
import br.com.midt.falemais.dto.request.SimulationRequest;
import br.com.midt.falemais.dto.response.SimulationResponse;

public interface AddSimulation {
    SimulationResponse add(SimulationRequest simulationRequest, Customer customer) throws Exception;
}
