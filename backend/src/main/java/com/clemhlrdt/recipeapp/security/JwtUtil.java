package com.clemhlrdt.recipeapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
	Utility class to generate tokens, validate tokens & get UserId from tokens
 */
@Component
public class JwtUtil {


	private String jwtSecret;

	private long expirationTime;

	public JwtUtil(@Value("${app.jwtSecret}") String jwtSecret, @Value("${app.expirationTime}") long expirationTime) {
		this.jwtSecret = jwtSecret;
		this.expirationTime = expirationTime;
	}

	/*
		GENERATE TOKEN
	 */

	public String generateToken(UserPrincipal userDetails, Date date) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userDetails.getId());
		return createToken(claims, userDetails.getEmail(), date);
	}

	protected String createToken(Map<String, Object> claims, String subject, Date date) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(date)
				.setExpiration(generateExpirationDate(date, expirationTime))
				.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))).compact();
	}

	protected Date generateExpirationDate(Date date, long milliseconds) {
		return new Date(date.getTime() + milliseconds);
	}

	/*
		EXTRACT INFO
	 */

	public String extractEmail(String token) throws SignatureException {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		long seconds = 3 * 60;
		return Jwts.parserBuilder()
							.setAllowedClockSkewSeconds(seconds)
							.setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder()
							.decode(jwtSecret)))
							.build()
							.parseClaimsJws(token)
							.getBody();
	}

	/*
		VERIFY TOKEN
	 */

	public Boolean validateToken(String token, UserPrincipal userDetails) {
		final String email = extractEmail(token);
		return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
