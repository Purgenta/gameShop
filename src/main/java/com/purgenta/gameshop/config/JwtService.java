package com.purgenta.gameshop.config;

import com.purgenta.gameshop.repositories.IUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.function.Function;


@Service()
public class JwtService {
    private static final String SIGN_KEY = "4A614E645267556B58703273357638792F423F4528482B4B6250655368566D59";

    @Autowired
    private IUserRepository userRepository;

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

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Set<String> userRoles = new HashSet<>();
        userRoles.add(userRepository.findByEmail(userDetails.getUsername()).getRole().name());
        extraClaims.put("Roles", userRoles);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
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

    public boolean validToken(String jwt, UserDetails userDetails) {
        final String email = extractEmail(jwt);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    public boolean isTokenExpired(String jwt) {
        return extractExpirationTime(jwt).before(new Date());
    }
}
