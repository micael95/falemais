package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.usecases.AddCustomer;
import br.com.uaubox.falemais.dto.request.CustomerRequest;
import br.com.uaubox.falemais.dto.response.CustomerResponse;
import br.com.uaubox.falemais.dto.response.ErrorResponse;
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
public class SignUpController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private AddCustomer addCustomer;

    @PostMapping
    public ResponseEntity<?> handle(@RequestBody @Valid CustomerRequest customerRequest) {
        try {
            CustomerResponse customerResponse = this.addCustomer.add(customerRequest);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        }catch ( Exception ex) {
            return ErrorResponse.handle(ex,LOGGER);
        }
    }

}
