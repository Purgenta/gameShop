package com.purgenta.gameshop.exceptionhandlers;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void invalidRoute(){}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public void notFound() {}
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> getInvalidArguments(MethodArgumentNotValidException args) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errors = args.getBindingResult().getFieldErrors();
        errors.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }
}
