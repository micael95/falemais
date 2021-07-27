package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.Plan;
import br.com.uaubox.falemais.domain.model.Simulation;
import br.com.uaubox.falemais.domain.repository.PlanRepository;
import br.com.uaubox.falemais.domain.repository.SimulationRepository;
import br.com.uaubox.falemais.domain.repository.TelephoneChargesRepository;
import br.com.uaubox.falemais.domain.usecases.AddSimulation;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.dto.response.SimulationResponse;
import br.com.uaubox.falemais.exception.EmailAlreadyExistsException;
import br.com.uaubox.falemais.exception.InvalidParamException;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import br.com.uaubox.falemais.factory.FactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddSimulationService extends FactoryManager implements AddSimulation {

    private final TelephoneChargesRepository telephoneChargesRepository;

    private final PlanRepository planRepository;

    @Autowired
    public AddSimulationService(TelephoneChargesRepository telephoneChargesRepository, PlanRepository planRepository) {
        this.telephoneChargesRepository = telephoneChargesRepository;
        this.planRepository = planRepository;
    }

    @Override
    public SimulationResponse add(SimulationRequest simulationRequest) throws InvalidParamException {

        Simulation simulation = getObjectFromRequest(simulationRequest, Simulation.class);
        System.out.println("simulationRequest.getPlanId() = " + simulationRequest.getPlanId());
        Optional<Plan> plan = this.planRepository.findById(simulationRequest.getPlanId());
        if(!plan.isPresent())
            throw new InvalidParamException("planId");

        return getResponseFromObject(simulation, SimulationResponse.class);
    }
}
