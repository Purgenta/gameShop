package com.purgenta.gameshop.exceptionhandlers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class JwtExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredJwtException.class)
    public JwtExpired expiredToken() {
        return new JwtExpired("Your token has expired");
    }

    @Data
    private static class JwtExpired {
        private final String message;

        public JwtExpired(String message) {
            this.message = message;
        }
    }
}
