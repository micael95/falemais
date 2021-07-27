package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.usecases.AddSimulation;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.SimulationResponse;
import br.com.uaubox.falemais.factory.FactoryManager;
import org.springframework.stereotype.Service;

@Service
public class AddSimulationService extends FactoryManager implements AddSimulation {
    @Override
    public SimulationResponse add(SimulationRequest simulationRequest) throws Exception {
        return new SimulationResponse();
    }
}
