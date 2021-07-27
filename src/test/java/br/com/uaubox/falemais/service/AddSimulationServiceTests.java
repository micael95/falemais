package br.com.uaubox.falemais.service;

import br.com.uaubox.falemais.domain.model.Plan;
import br.com.uaubox.falemais.domain.model.TelephoneCharges;
import br.com.uaubox.falemais.domain.repository.PlanRepository;
import br.com.uaubox.falemais.domain.repository.TelephoneChargesRepository;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.exception.InvalidParamException;
import com.github.javafaker.Faker;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddSimulationServiceTests {


    @Autowired
    private TelephoneChargesRepository telephoneChargesRepository;
    
    @Autowired
    private PlanRepository planRepository;

    private final Faker faker = new Faker();
    private final ModelMapper modelMapper = new ModelMapper();

    private AddSimulationService addSimulationService;

    @BeforeEach
    public void beforeEach() {
        List<Plan> planList = planRepository.findAll();
        MockitoAnnotations.openMocks(this);
        this.addSimulationService = new AddSimulationService(telephoneChargesRepository,planRepository);
    }

    @Test
    public void shouldReturnAnExceptionIfPlanDoesNotExists(){

        TelephoneCharges telephoneCharges = new TelephoneCharges();
        telephoneCharges.setOrigin(Integer.parseInt(faker.phoneNumber().subscriberNumber(2)));
        telephoneCharges.setDestination(Integer.parseInt(faker.phoneNumber().subscriberNumber(2)));
        telephoneCharges.setPerMinuteRate(new BigDecimal(faker.number().digits(2)));
        telephoneChargesRepository.save(telephoneCharges);

        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setOrigin(telephoneCharges.getOrigin());
        simulationRequest.setDestination(telephoneCharges.getDestination());
        simulationRequest.setTimeInMinutes(Integer.valueOf(faker.number().digits(2)));
        simulationRequest.setPlanId("invalid_plan_id");

        try {
            this.addSimulationService.add(simulationRequest);
            Assertions.fail("exception not thrown");
        } catch (InvalidParamException ignored) {}

    }
}
