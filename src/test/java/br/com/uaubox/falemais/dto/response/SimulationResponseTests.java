package br.com.uaubox.falemais.dto.response;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.Simulation;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class SimulationResponseTests {
    private final Faker faker = new Faker();
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertSimulationModelToSimulationResponse() {
        Simulation simulation = new Simulation();
        simulation.setOrigin(faker.number().randomDigit());
        simulation.setDestination(faker.number().randomDigit());
        simulation.setTimeInMinutes(faker.number().randomDigit());

        SimulationResponse simulationResponse = modelMapper.map(simulation, SimulationResponse.class);
        Assertions.assertEquals(simulationResponse.getOrigin(), simulation.getOrigin());
        Assertions.assertEquals(simulationResponse.getDestination(), simulation.getDestination());
        Assertions.assertEquals(simulationResponse.getTimeInMinutes(), simulation.getTimeInMinutes());
    }
}
