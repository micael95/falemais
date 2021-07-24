package br.com.uaubox.falemais.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/signup")
public class SignUpController {

    @PostMapping()
    public ResponseEntity<Object> handle(@RequestBody @Valid Object form) {
        return ResponseEntity.badRequest().build();
    }

}
