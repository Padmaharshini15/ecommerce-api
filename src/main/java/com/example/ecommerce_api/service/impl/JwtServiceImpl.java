package com.example.ecommerce_api.service.impl;

import com.example.ecommerce_api.exception.JwtValidationException;
import com.example.ecommerce_api.service.inf.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    @Override
    public String generateToken(String email) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    @Override
    public SecretKey getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(
                java.util.Base64.getEncoder()
                        .encodeToString(SECRET.getBytes())
        );

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {

        final String email = extractUserEmail(token);

        return email.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    @Override
    public String extractUserEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimResolver
    ) {

        try {

            final Claims claims = extractAllClaims(token);

            return claimResolver.apply(claims);

        } catch (Exception ex) {

            throw new JwtValidationException("JWT Validation Exception");
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {

        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }
}