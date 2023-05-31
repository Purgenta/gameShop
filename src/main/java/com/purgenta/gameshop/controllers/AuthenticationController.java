package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.requests.authentication.LoginRequest;
import com.purgenta.gameshop.requests.authentication.RegisterRequest;
import com.purgenta.gameshop.response.authentication.AuthenticationResponse;
import com.purgenta.gameshop.services.authentication.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(request,response));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        return authenticationService.login(request,response);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(@NotNull HttpServletRequest request,HttpServletResponse response) {
        try {
            authenticationService.refreshToken(request,response);
        }
        catch (IOException exception) {
            response.setStatus(401);
        }
    }
    @GetMapping("/logout")
    public void logout(@NotNull HttpServletResponse response) {
        authenticationService.logout(response);
        response.setStatus(200);
    }

}
