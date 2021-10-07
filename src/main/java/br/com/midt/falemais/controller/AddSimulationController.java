package br.com.midt.falemais.controller;

import br.com.midt.falemais.domain.model.Customer;
import br.com.midt.falemais.domain.usecases.AddSimulation;
import br.com.midt.falemais.dto.request.SimulationRequest;
import br.com.midt.falemais.dto.response.ApiResponseMessage;
import br.com.midt.falemais.dto.response.ErrorResponse;
import br.com.midt.falemais.dto.response.SimulationResponse;
import br.com.midt.falemais.dto.response.ValidationResponse;
import br.com.midt.falemais.dto.response.*;
import br.com.midt.falemais.exception.InvalidParamException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/simulation")
@Api(tags = "Simulação", description = "Api utilizada para dados referente à simulação")
public class AddSimulationController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AddSimulationController.class);

    @Autowired
    private AddSimulation addSimulation;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Endpoint utilizado para calcular e registrar uma simulação", authorizations = {@Authorization(value = "Authorization Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization Bearer",
                    required = true, dataType = "string", paramType = "header") })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Simulação registrada com sucesso", response = SimulationResponse.class),
            @ApiResponse(code = 400, message = "Dados informados inválidos", response = ValidationResponse.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Token de acesso inválido", response = ApiResponseMessage.class),
            @ApiResponse(code = 406, message = "Plano de tarifa inválido", response = ApiResponseMessage.class),
            @ApiResponse(code = 500, message = "Erro interno no servidor", response = ApiResponseMessage.class),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> handle(@ApiParam(name = "Simulação", value = "Dados da simulação", required = true)
                                    @RequestBody @Valid SimulationRequest simulationRequest, HttpServletRequest request) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = new Customer();
            if (principal instanceof UserDetails) {
                customer = ((Customer) principal);
            }

            SimulationResponse simulationResponse = this.addSimulation.add(simulationRequest, customer);
            return new ResponseEntity<>(simulationResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex instanceof InvalidParamException)
                return ErrorResponse.handle(((InvalidParamException) ex).getCode(), ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);

            return ErrorResponse.handle(ex, LOGGER);
        }
    }

}
