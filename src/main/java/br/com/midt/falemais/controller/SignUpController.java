package br.com.midt.falemais.controller;

import br.com.midt.falemais.exception.InvalidPasswordConfirmationException;
import br.com.midt.falemais.domain.usecases.AddCustomer;
import br.com.midt.falemais.dto.request.CustomerRequest;
import br.com.midt.falemais.dto.response.ApiResponseMessage;
import br.com.midt.falemais.dto.response.CustomerResponse;
import br.com.midt.falemais.dto.response.ErrorResponse;
import br.com.midt.falemais.dto.response.ValidationResponse;
import br.com.midt.falemais.exception.EmailAlreadyExistsException;
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
@RequestMapping("/api/v1/signup")
@Api(tags = "Cadastro", description = "Api utilizada para registro de clientes")
public class SignUpController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);
    @Autowired
    private AddCustomer addCustomer;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Endpoint utilizado para registrar um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente cadastrado com sucesso",response = CustomerResponse.class),
            @ApiResponse(code = 400, message = "Dados informados inválidos", response = ValidationResponse.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "Confirmação de senha inválida", response = ApiResponseMessage.class),
            @ApiResponse(code = 409, message = "Email já cadastrado em nossa base de dados", response = ApiResponseMessage.class),
            @ApiResponse(code = 500, message = "Erro interno no servidor", response = ApiResponseMessage.class),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> handle(
            @ApiParam(name = "Cliente", value = "Dados do cliente", required = true)
            @RequestBody @Valid CustomerRequest customerRequest) {
        try {
            CustomerResponse customerResponse = this.addCustomer.add(customerRequest);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof InvalidPasswordConfirmationException)
                return ErrorResponse.handle(((InvalidPasswordConfirmationException) ex).getCode(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

            if (ex instanceof EmailAlreadyExistsException)
                return ErrorResponse.handle(((EmailAlreadyExistsException) ex).getCode(), ex.getMessage(), HttpStatus.CONFLICT);

            return ErrorResponse.handle(ex, LOGGER);
        }
    }

}
