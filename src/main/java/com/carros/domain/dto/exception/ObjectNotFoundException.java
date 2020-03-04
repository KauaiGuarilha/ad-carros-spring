package com.carros.domain.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String messege) {
        super(messege);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
