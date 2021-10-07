package br.com.midt.falemais.service;

import br.com.midt.falemais.domain.model.Customer;
import br.com.midt.falemais.domain.usecases.TokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManagerService implements TokenManager {

    @Value("${falemais.jwt.expiration}")
    private String expiration;

    @Value("${falemais.jwt.secret}")
    private String secret;

    @Override
    public String generateToken(Authentication authentication) {
        Customer loggedCustomer = (Customer) authentication.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Fale Mais API")
                .setSubject(loggedCustomer.getCustomerId())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getCustomerId(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
