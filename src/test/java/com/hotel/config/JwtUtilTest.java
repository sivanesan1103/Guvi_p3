package com.hotel.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilTest {

	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		jwtUtil = new JwtUtil();
		ReflectionTestUtils.setField(jwtUtil, "secret", "this-is-a-test-secret-key-for-jwt-token-generation");
		ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 86400000L);
	}

	@Test
	void generateToken_WithRole() {
		String token = jwtUtil.generateToken("1", "ROLE_ADMIN");

		assertNotNull(token);
		assertTrue(token.length() > 0);
	}

	@Test
	void generateToken_WithoutRole() {
		String token = jwtUtil.generateToken("1");

		assertNotNull(token);
		assertTrue(token.length() > 0);
	}

	@Test
	void extractUsername() {
		String token = jwtUtil.generateToken("123");

		String username = jwtUtil.extractUsername(token);

		assertEquals("123", username);
	}

	@Test
	void extractRole() {
		String token = jwtUtil.generateToken("1", "ROLE_ADMIN");

		String role = jwtUtil.extractRole(token);

		assertEquals("ROLE_ADMIN", role);
	}

	@Test
	void extractRole_WithNullRole() {
		String token = jwtUtil.generateToken("1", null);

		String role = jwtUtil.extractRole(token);

		assertNull(role);
	}

	@Test
	void validateToken_Valid() {
		String token = jwtUtil.generateToken("123");

		boolean isValid = jwtUtil.validateToken(token, "123");

		assertTrue(isValid);
	}

	@Test
	void validateToken_InvalidUsername() {
		String token = jwtUtil.generateToken("123");

		boolean isValid = jwtUtil.validateToken(token, "456");

		assertFalse(isValid);
	}

	@Test
	void validateToken_Expired() {
		JwtUtil shortExpirationJwtUtil = new JwtUtil();
		ReflectionTestUtils.setField(shortExpirationJwtUtil, "secret", "this-is-a-test-secret-key-for-jwt-token-generation");
		ReflectionTestUtils.setField(shortExpirationJwtUtil, "jwtExpirationMs", -100L);

		String token = shortExpirationJwtUtil.generateToken("123");

		assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
			shortExpirationJwtUtil.validateToken(token, "123");
		});
	}
}