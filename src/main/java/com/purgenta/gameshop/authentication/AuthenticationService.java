package com.purgenta.gameshop.authentication;

import com.purgenta.gameshop.models.Role;
import com.purgenta.gameshop.models.UserModel;
import com.purgenta.gameshop.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service()
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user =
                UserModel.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.ROLE_USER)
                        .build();
        userRepository.save(user);
        var accessToken = jwtService.generateToken(user, jwtService.getAccessTokenTime(), "accessToken");
        var refreshToken = jwtService.generateToken(user, jwtService.getRefreshTokenTime(), "refreshToken");
        return AuthenticationResponse.builder().accessToken(accessToken).role(Role.ROLE_USER.name()).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail());
        var accessToken = jwtService.generateToken(user, jwtService.getAccessTokenTime(), "accessToken");
        var refreshToken = jwtService.generateToken(user, jwtService.getRefreshTokenTime(), "refreshToken");
        return AuthenticationResponse.builder().accessToken(accessToken).role(user.getRole().name()).refreshToken(refreshToken).build();
    }
}
