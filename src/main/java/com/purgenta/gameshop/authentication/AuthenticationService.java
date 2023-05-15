package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.models.Role;
import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.Cookie;

@Service()
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        var user =
                User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.ROLE_USER)
                        .registered_at(LocalDate.now())
                        .build();
        userRepository.save(user);
        var accessToken = jwtService.generateToken(user, jwtService.getAccessTokenTime(), "accessToken");
        var refreshToken = jwtService.generateToken(user, jwtService.getRefreshTokenTime(), "refreshToken");
        setRefreshTokenCookie(refreshToken,response);
        return AuthenticationResponse.builder().accessToken(accessToken).role(Role.ROLE_USER.name()).build();
    }

    public ResponseEntity<?> login(LoginRequest request,HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword())
            );
        } catch (BadCredentialsException exception) {
            Map<String, String> responseBadCredentials = new HashMap<>();
            responseBadCredentials.put("errorMessage", "Bad credentials");
            return new ResponseEntity<>(responseBadCredentials, HttpStatus.BAD_REQUEST);
        }
        var user = userRepository.findByEmail(request.getEmail());
        var accessToken = jwtService.generateToken(user, jwtService.getAccessTokenTime(), "accessToken");
        var refreshToken = jwtService.generateToken(user, jwtService.getRefreshTokenTime(), "refreshToken");
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().accessToken(accessToken).role(user.getRole().name()).build();
        setRefreshTokenCookie(refreshToken,response);
        return ResponseEntity.ok(authenticationResponse);
    }
    public void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        final Cookie cookie = new Cookie("refresh", refreshToken);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
