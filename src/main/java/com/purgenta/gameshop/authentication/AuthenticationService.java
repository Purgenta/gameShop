package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.models.Role;
import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IUserRepository;
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

@Service()
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
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
        return AuthenticationResponse.builder().accessToken(accessToken).role(Role.ROLE_USER.name()).refreshToken(refreshToken).build();
    }

    public ResponseEntity<?> login(LoginRequest request) {
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
        AuthenticationResponse response = AuthenticationResponse.builder().accessToken(accessToken).role(user.getRole().name()).refreshToken(refreshToken).build();
        return ResponseEntity.ok(response);
    }
}
