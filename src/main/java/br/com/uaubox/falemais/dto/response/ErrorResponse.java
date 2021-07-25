package br.com.uaubox.falemais.dto.response;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    public static ResponseEntity<ApiResponseMessage> handle(Exception e, Logger logger) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ApiResponseMessage("internal-server-error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResponseMessage> handle(String code, String message, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponseMessage(code, message), status);
    }
}
