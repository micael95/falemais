package br.com.midt.falemais.config.security;

import br.com.midt.falemais.domain.model.Customer;
import br.com.midt.falemais.domain.repository.CustomerRepository;
import br.com.midt.falemais.domain.usecases.TokenManager;
import br.com.midt.falemais.service.TokenManagerService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManagerService;
    private final CustomerRepository customerRepository;

    public AuthenticationByTokenFilter(TokenManagerService tokenManagerService, CustomerRepository customerRepository) {
        this.tokenManagerService = tokenManagerService;
        this.customerRepository = customerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(httpServletRequest);
        boolean valid = tokenManagerService.isValidToken(token);
        if (valid) {
            authCustomer(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authCustomer(String token) {
        String customerId = tokenManagerService.getCustomerId(token);
        Customer customer = customerRepository.findById(customerId).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customer, null, customer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
