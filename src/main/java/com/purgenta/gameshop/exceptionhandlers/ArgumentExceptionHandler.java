package com.purgenta.gameshop.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ArgumentExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> getInvalidArguments(MethodArgumentNotValidException args) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errors = args.getBindingResult().getFieldErrors();
        errors.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }
}
