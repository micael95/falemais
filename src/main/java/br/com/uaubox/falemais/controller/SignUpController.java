package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.usecases.AddCustomer;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.dto.response.ErrorResponse;
import br.com.uaubox.falemais.exception.EmailAlreadyExistsException;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/signup")
@Api(tags = "Cadastro", description = "Api utilizada para registro de clientes")
public class SignUpController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);
    @Autowired
    private AddCustomer addCustomer;

    @PostMapping
    @ApiOperation(value="Endpoint utilizado para registrar um cliente")
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
