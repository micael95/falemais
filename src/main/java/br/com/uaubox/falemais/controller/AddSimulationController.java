package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.usecases.AddSimulation;
import br.com.uaubox.falemais.dto.request.SimulationRequest;
import br.com.uaubox.falemais.dto.response.*;
import br.com.uaubox.falemais.exception.EmailAlreadyExistsException;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/simulation")
@Api(tags = "Simulation", description = "Api utilizada para dados referente à simulação")
public class AddSimulationController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AddSimulationController.class);

    @Autowired
    private AddSimulation addSimulation;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Endpoint utilizado para calcular e registrar uma simulação")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Simulação registrada com sucesso", response = CustomerResponse.class),
            @ApiResponse(code = 400, message = "Dados informados inválidos", response = ValidationResponse.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Token de acesso inválido", response = ApiResponseMessage.class),
            @ApiResponse(code = 406, message = "Plano de tarifa inválido", response = ApiResponseMessage.class),
            @ApiResponse(code = 500, message = "Erro interno no servidor", response = ApiResponseMessage.class),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> handle(@ApiParam(name = "Simulação", value = "Dados da simulação", required = true)
                                    @RequestBody @Valid SimulationRequest simulationRequest) {
        try {
            SimulationResponse simulationResponse = this.addSimulation.add(simulationRequest);
            return new ResponseEntity<>(simulationResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof InvalidPasswordConfirmationException)
                return ErrorResponse.handle(((InvalidPasswordConfirmationException) ex).getCode(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

            if (ex instanceof EmailAlreadyExistsException)
                return ErrorResponse.handle(((EmailAlreadyExistsException) ex).getCode(), ex.getMessage(), HttpStatus.CONFLICT);

            return ErrorResponse.handle(ex, LOGGER);
        }
    }

}
