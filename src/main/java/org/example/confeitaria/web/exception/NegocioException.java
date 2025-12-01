package org.example.confeitaria.web.exception;

import org.springframework.http.HttpStatus;

public class NegocioException extends RuntimeException {

    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public NegocioException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}