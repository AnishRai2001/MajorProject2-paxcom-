package com.example.jwtService.Service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {
	
    private static final String SECRET_KEY = "YourSecretKeyHere";
    private static final long EXPIRATION_TIME = 600_000;

	
	public String generateToken(String Username,String role) {
		return JWT.create()
				.withSubject(Username)
				.withClaim("role", role)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC256(SECRET_KEY));
	}

	public String verifyTokenAndGetSubject(String token) {
		return JWT
				.require(Algorithm.HMAC256(SECRET_KEY))
				.build()
				.verify(token)
				.getSubject();
	}
}
