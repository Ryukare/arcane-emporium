package com.arcaneemporium.backend.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService() {
        this.key = Jwts.SIG.HS256.key().build();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
