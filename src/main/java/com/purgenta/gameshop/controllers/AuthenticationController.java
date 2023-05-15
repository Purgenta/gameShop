package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.authentication.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(request,response));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request,HttpServletResponse response) {
        return authenticationService.login(request,response);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> refreshToken(@NotNull HttpServletRequest request,HttpServletResponse response) {
        return jwtService.processRefreshToken(request,response);
    }

}
