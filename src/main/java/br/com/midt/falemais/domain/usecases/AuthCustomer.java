package br.com.midt.falemais.domain.usecases;

import br.com.midt.falemais.dto.request.TokenRequest;
import br.com.midt.falemais.dto.response.TokenResponse;
import org.springframework.security.core.AuthenticationException;

public interface AuthCustomer {
    TokenResponse authenticate(TokenRequest tokenRequest) throws AuthenticationException;
}
