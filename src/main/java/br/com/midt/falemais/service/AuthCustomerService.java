package br.com.midt.falemais.service;

import br.com.midt.falemais.domain.usecases.AuthCustomer;
import br.com.midt.falemais.domain.usecases.TokenManager;
import br.com.midt.falemais.dto.request.TokenRequest;
import br.com.midt.falemais.dto.response.TokenResponse;
import br.com.midt.falemais.factory.FactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthCustomerService extends FactoryManager implements AuthCustomer {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public TokenResponse authenticate(TokenRequest tokenRequest) throws AuthenticationException {
        UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(tokenRequest.getEmail(), tokenRequest.getPassword());
        Authentication authentication = authManager.authenticate(loginData);
        String token = tokenManager.generateToken(authentication);
        return new TokenResponse(token, "Bearer");
    }
}
