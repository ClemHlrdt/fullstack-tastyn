package com.clemhlrdt.recipeapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource
class JwtUtilTest {


	long expirationTime = 86400000;
	String jwtSecret="hz+RApie0uihf92WO6Iu0rv1Xp4QGroZ/9lyxOYbfKU=";

	JwtUtil jwtUtil = new JwtUtil(jwtSecret, expirationTime);

	UserPrincipal userPrincipal;

	@BeforeEach
	void setUp() {
		userPrincipal = new UserPrincipal(
				UUID.randomUUID(),
				"clem",
				"clement.helardot@gmail.com",
				"password",
				Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
	}

	@Test
	void createToken() {
		// Arrange
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userPrincipal.getId());
		String subject = userPrincipal.getEmail();
		Date currentDate = new Date();

		String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(currentDate)
				.setExpiration(new Date(currentDate.getTime() + expirationTime))
				.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))).compact();
		// Act
		String actualToken = jwtUtil.createToken(claims, subject, currentDate);
		// Assert
		assertThat(actualToken).isEqualTo(token);
	}

	@Test
	void generateToken() {
		// Arrange
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userPrincipal.getId());
		String subject = userPrincipal.getEmail();
		Date currentDate = new Date();
		String token = jwtUtil.createToken(claims, subject, currentDate);
		// Act
		String actualToken = jwtUtil.generateToken(userPrincipal, currentDate);
		// Assert
		assertThat(actualToken).isEqualTo(token);
	}

	@Test
	void generateExpirationDate() {
		// Arrange
		Date date = new Date();
		long expirationTime = 86400000;

		// Act
		Date expirationDate = jwtUtil.generateExpirationDate(date, expirationTime);

		// Assert
		assertThat(expirationDate).isEqualTo(new Date(date.getTime() + 86400000));
	}

	@Test
	void extractEmail() {
		// Arrange
		String email = userPrincipal.getEmail();
		String token = jwtUtil.generateToken(userPrincipal, new Date());
		// Act
		String extractedEmail = jwtUtil.extractEmail(token);
		// Assert
		assertThat(extractedEmail).isEqualTo(email);
	}

	@Test
	void extractExpiration() {
		// Arrange
		Date actualDate = new Date();
		long skewMilliseconds = 3 * 60 * 1000;
		String token = jwtUtil.generateToken(userPrincipal, actualDate);
		Date expectedExpirationDate = new Date(actualDate.getTime() + expirationTime + skewMilliseconds);

		// Act
		Date expirationDate = jwtUtil.extractExpiration(token);

		// Assert
		assertThat(expirationDate).isAfter(new Date(actualDate.getTime() + expirationTime - skewMilliseconds));
		assertThat(expirationDate).isBeforeOrEqualTo(expectedExpirationDate);
	}

	@Test
	void extractEmailClaim() {
		// Arrange

		String token = jwtUtil.generateToken(userPrincipal, new Date());
		// Act
		String userEmail = jwtUtil.extractClaim(token, Claims::getSubject);

		// Assert
		assertThat(userEmail).isEqualTo(userPrincipal.getEmail());
	}

	@Test
	void extractExpirationClaim() {
		// Arrange
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + expirationTime);
		String token = jwtUtil.generateToken(userPrincipal, currentDate);
		// Act
		Date expiration = jwtUtil.extractClaim(token, Claims::getExpiration);

		// Assert
		assertThat(expiration).isBeforeOrEqualTo(expirationDate);
	}

	@Test
	void validateToken() {
		// Arrange
		String token = jwtUtil.generateToken(userPrincipal, new Date());
		// Act
		Boolean isValid = jwtUtil.validateToken(token, userPrincipal);

		// Assert
		assertTrue(isValid);
	}

	@Test
	void checkInvalidToken() {
		// Arrange
		String token = jwtUtil.generateToken(userPrincipal, new Date());
		// Act
		userPrincipal.setEmail("anotheremail@gmail.com");
		Boolean isValid = jwtUtil.validateToken(token, userPrincipal);

		// Assert
		assertFalse(isValid);
	}
}