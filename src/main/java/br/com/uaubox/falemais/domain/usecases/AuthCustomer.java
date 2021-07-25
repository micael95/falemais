package br.com.uaubox.falemais.domain.usecases;

import br.com.uaubox.falemais.dto.request.TokenRequest;
import br.com.uaubox.falemais.dto.response.TokenResponse;
import org.springframework.security.core.AuthenticationException;

public interface AuthCustomer {
    TokenResponse authenticate(TokenRequest tokenRequest) throws AuthenticationException;
}
