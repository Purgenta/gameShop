package com.purgenta.gameshop.services.authentication;

import com.purgenta.gameshop.requests.authentication.LoginRequest;
import com.purgenta.gameshop.requests.authentication.RegisterRequest;
import com.purgenta.gameshop.response.authentication.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface IAuthenticationService {
    void logout(HttpServletResponse response);
    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
    AuthenticationResponse register(RegisterRequest request, HttpServletResponse response);
    ResponseEntity<?> login(LoginRequest request, HttpServletResponse response);
}
