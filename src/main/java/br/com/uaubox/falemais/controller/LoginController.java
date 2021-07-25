package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.usecases.AuthCustomer;
import br.com.uaubox.falemais.domain.usecases.TokenManager;
import br.com.uaubox.falemais.dto.request.TokenRequest;
import br.com.uaubox.falemais.dto.response.*;
import br.com.uaubox.falemais.exception.InvalidPasswordConfirmationException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/oauth/token")
@Api(tags = "Login", description = "Api utilizada para realizar o login no simulador")
public class LoginController {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthCustomer authCustomer;

    @PostMapping(produces = "application/json")
    @ApiOperation(value = "Endpoint utilizado para realizar o login no simulador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login realizado com sucesso", response = TokenResponse.class),
            @ApiResponse(code = 400, message = "Dados informados inválidos", response = ValidationResponse.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Credencias inválidas", response = ApiResponseMessage.class),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção", response = ApiResponseMessage.class),
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> handle(
            @ApiParam(name = "Login", value = "Dados de login", required = true)
            @Valid @RequestBody TokenRequest tokenRequest) {
        try {
            TokenResponse tokenResponse = this.authCustomer.authenticate(tokenRequest);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }catch ( Exception ex) {
            if (ex instanceof AuthenticationException)
                return ErrorResponse.handle("invalid-credentials", ex.getMessage(), HttpStatus.UNAUTHORIZED);
            return ErrorResponse.handle(ex, LOGGER);
        }
    }
}
