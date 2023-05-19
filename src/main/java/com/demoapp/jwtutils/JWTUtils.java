package com.demoapp.jwtutils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.demoapp.application.CustomProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component 
public class JWTUtils {

	private String jwtSecret = "somesecret";
	
	@Autowired
	private CustomProperties appProperties;
	
	/**
	 * @description
	 */
	public String generateJwtToken(String username) {
		
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +  appProperties.getTimeout()))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String generateRefreshToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +  appProperties.getTimeout()+ appProperties.getRefreshTimeout()))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * @description:
	 */
	public Boolean validateJwtToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		Boolean isTokenExpired = claims.getExpiration().before(new Date());
		return (username.equals(userDetails.getUsername()) && !isTokenExpired);
	}

	/**
	 * @description
	 */
	public String getUsernameFromToken(String token) {
		final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
}
