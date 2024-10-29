package com.billingsystem.utility;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTUtil {
	private static final String SECRET = System.getenv("JWT_SECRET_KEY");
	private static final long EXP_TIME = 5 * 60 * 60 * 1000;
	
	public static String generateToken(String phoneNumber, String role, String clientId) {
		return JWT.create().withSubject(phoneNumber)
				.withClaim("role", role) 
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+EXP_TIME)).withIssuer(clientId)
				.sign(Algorithm.HMAC256(SECRET));
	}
	
	public static DecodedJWT validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			return verifier.verify(token);
		}catch(JWTVerificationException jwtVerificationException) {
			return null;
		}
		
	}
	
	public static String getPhoneNumber(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject();
	}
	public static String getRole(DecodedJWT decodedJWT) {
		return decodedJWT.getClaim("role").asString();
	}

	public static String getClientIp(DecodedJWT decodedJWT) {
		return decodedJWT.getIssuer();
	}
}
