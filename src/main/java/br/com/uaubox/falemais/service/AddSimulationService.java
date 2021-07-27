package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.Plan;
import br.com.uaubox.falemais.domain.model.Simulation;
import br.com.uaubox.falemais.domain.model.TelephoneCharges;
import br.com.uaubox.falemais.domain.repository.PlanRepository;
import br.com.uaubox.falemais.domain.repository.SimulationRepository;
import br.com.uaubox.falemais.domain.repository.TelephoneChargesRepository;
import br.com.uaubox.falemais.domain.usecases.AddSimulation;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.SimulationResponse;
import br.com.uaubox.falemais.exception.InvalidParamException;
import br.com.uaubox.falemais.factory.FactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class AddSimulationService extends FactoryManager implements AddSimulation {

    private final TelephoneChargesRepository telephoneChargesRepository;

    private final PlanRepository planRepository;

    private final SimulationRepository simulationRepository;

    @Autowired
    public AddSimulationService(SimulationRepository simulationRepository,
                                TelephoneChargesRepository telephoneChargesRepository,
                                PlanRepository planRepository) {
        this.telephoneChargesRepository = telephoneChargesRepository;
        this.planRepository = planRepository;
        this.simulationRepository = simulationRepository;
    }


    @Transactional
    @Override
    public SimulationResponse add(SimulationRequest simulationRequest, Customer customer) throws InvalidParamException, Exception {
        System.out.println("chegou aqui");
        Simulation simulation = getObjectFromRequest(simulationRequest, Simulation.class);
        Optional<Plan> selectedPlan = this.planRepository.findByPlanIdAndActive(simulationRequest.getPlanId(), true);
        if (!selectedPlan.isPresent())
            throw new InvalidParamException("planId");

        Optional<TelephoneCharges> telephoneCharges = this.telephoneChargesRepository
                .findByOriginAndDestination(simulation.getOrigin(), simulation.getDestination());

        if (!telephoneCharges.isPresent())
            throw new InvalidParamException("Origin and destination not found");

        Integer timeInMinutesRequest = simulationRequest.getTimeInMinutes();
        Integer timeInMinutesPlan = selectedPlan.get().getMinutes();

        int timeInMinutes = timeInMinutesPlan - timeInMinutesRequest;

        if (timeInMinutes < 0) {
            timeInMinutes = -timeInMinutes;
            double perMinuteRate = telephoneCharges.get().getPerMinuteRate().doubleValue();
            double price = timeInMinutes * (perMinuteRate + (perMinuteRate * (selectedPlan.get().getSurplusPercentage().doubleValue() / 100)));
            simulation.setPrice(new BigDecimal(price).setScale(2, RoundingMode.HALF_DOWN));
        } else {
            simulation.setPrice(new BigDecimal("0.00"));
        }

        simulation.setTimeInMinutes(timeInMinutesRequest);
        simulation.setCustomer(customer);
        simulation.setPlan(selectedPlan.get());

        this.simulationRepository.save(simulation);

        return getResponseFromObject(simulation, SimulationResponse.class);
    }
}
