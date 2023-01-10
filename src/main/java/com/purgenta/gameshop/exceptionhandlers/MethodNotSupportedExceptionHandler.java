package com.purgenta.gameshop.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MethodNotSupportedExceptionHandler {
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, String> invalidRoute() {
        Map<String, String> invalidRequest = new HashMap<>();
        invalidRequest.put("errorMessage", "method not supported on the given route");
        return invalidRequest;
    }
}
