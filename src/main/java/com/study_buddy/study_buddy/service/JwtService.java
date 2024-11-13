package com.study_buddy.study_buddy.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private String secret = "yourSecretKeyHere";

    private Long expiration = 3600L;

    public JwtService() {
    }


    // Method to generate a JWT token
    public String generateToken(String email) {
        System.out.println("Expiration value: " + expiration);
        return JWT.create()
                .withSubject(email)  // Set the subject as the user's email or ID
                .withIssuedAt(new Date())  // Set the issue time
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration * 1000))  // Set the expiration time
                .sign(Algorithm.HMAC256(secret));  // Sign with HMAC and the secret key
    }
}

