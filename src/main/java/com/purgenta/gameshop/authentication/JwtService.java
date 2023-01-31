package com.purgenta.gameshop.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service()
@RequiredArgsConstructor
public class JwtService {
    private static final String SIGN_KEY = "4A614E645267556B58703273357638792F423F4528482B4B6250655368566D59";
    private final UserDetailsService userDetailsService;

    @Getter
    private final int accessTokenTime = 86400000;
    @Getter
    private final int refreshTokenTime = 86400000;

    public String extractEmail(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date extractExpirationTime(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, int time, String tokenType) {
        return generateToken(new HashMap<>(), userDetails, time, tokenType);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, int time, String tokenType) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("tokenType", tokenType)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwt).getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SIGN_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean validToken(String jwt, UserDetails userDetails, String tokenType) {
        final String email = extractEmail(jwt);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(jwt) && extractTokenType(jwt).equals(tokenType));
    }

    public boolean isTokenExpired(String jwt) {
        return extractExpirationTime(jwt).before(new Date());
    }

    public ResponseEntity<Map<String, String>> processRefreshToken(HttpServletRequest request) {
        Map<String, String> refreshResponse = new HashMap<>();
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(refreshResponse, HttpStatus.BAD_REQUEST);
        }
        final String jwt = authorizationHeader.substring(7);
        final String email = extractEmail(jwt);
        if (email == null && SecurityContextHolder.getContext().getAuthentication() != null) {
            return new ResponseEntity<>(refreshResponse, HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (validToken(jwt, userDetails, "refreshToken")) {
            refreshResponse.put("accessToken", generateToken(userDetails, getAccessTokenTime(), "accessToken"));
            refreshResponse.put("role", userDetails.getAuthorities().toArray()[0].toString());
            return new ResponseEntity<>(refreshResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(refreshResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public String extractTokenType(String jwt) {
        return (String) extractClaims(jwt).get("tokenType");
    }
}
