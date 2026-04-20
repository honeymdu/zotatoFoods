package com.food.zotatoFoods.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import com.food.zotatoFoods.entites.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${jwt.secretkey}")
    private String JwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(JwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String GenerateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("UserEmail", user.getEmail())
                .claim("Roles", user.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 40))
                .signWith(getSecretKey())
                .compact();

    }

    public String GenerateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 6))
                .signWith(getSecretKey())
                .compact();

    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }

}
