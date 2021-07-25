package br.com.uaubox.falemais.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/token")
@Api(tags = "Login", description = "Api utilizada para realizar o login no simulador")
public class LoginController {

    @PostMapping
    public ResponseEntity<?> handle() {
        return ResponseEntity.badRequest().build();
    }
}
