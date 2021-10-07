package br.com.midt.falemais.config.validation;

import br.com.midt.falemais.dto.response.ApiResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class HttpUnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(this.httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage(String.valueOf(this.httpStatus.value()), this.httpStatus.getReasonPhrase());
        response.getWriter().write(objectMapper.writeValueAsString(apiResponseMessage));
    }
}