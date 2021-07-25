package br.com.uaubox.falemais.domain.usecases;

import org.springframework.security.core.Authentication;

public interface TokenManager {
    String generateToken(Authentication authentication);
    boolean isValidToken(String token);
    String getCustomerId(String token);
}
