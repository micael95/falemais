package br.com.uaubox.falemais.dto.request;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.Simulation;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class SimulationRequestTests {
    private final Faker faker = new Faker();
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertSimulationRequestToSimulationModel() {
        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setPlanId(faker.lorem().characters(100));
        simulationRequest.setOrigin(faker.number().randomDigit());
        simulationRequest.setDestination(faker.number().randomDigit());
        simulationRequest.setTimeInMinutes(faker.number().randomDigit());

        Simulation simulation = modelMapper.map(simulationRequest, Simulation.class);
        Assertions.assertEquals(simulation.getOrigin(), simulationRequest.getOrigin());
        Assertions.assertEquals(simulation.getDestination(), simulationRequest.getDestination());
        Assertions.assertEquals(simulation.getTimeInMinutes(), simulationRequest.getTimeInMinutes());
    }
}
