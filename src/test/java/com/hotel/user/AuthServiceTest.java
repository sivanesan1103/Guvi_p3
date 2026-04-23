package com.hotel.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotel.config.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtUtil jwtUtil;

	private AuthService authService;

	@BeforeEach
	void setUp() {
		authService = new AuthService(userRepository, passwordEncoder, jwtUtil);
	}

	@Test
	void register_Success() {
		RegisterRequest request = new RegisterRequest();
		request.setName("Test User");
		request.setEmail("test@example.com");
		request.setPassword("password123");

		when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
		when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
			User user = invocation.getArgument(0);
			user.setId(1L);
			return user;
		});
		when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("token");

		AuthResponse response = authService.register(request);

		assertNotNull(response);
		assertEquals("token", response.getToken());
		assertEquals("ROLE_USER", response.getRole());
		verify(userRepository).save(any(User.class));
	}

	@Test
	void register_EmailAlreadyExists() {
		RegisterRequest request = new RegisterRequest();
		request.setName("Test User");
		request.setEmail("existing@example.com");
		request.setPassword("password123");

		when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

		assertThrows(Exception.class, () -> authService.register(request));
	}

	@Test
	void register_NullRequest() {
		assertThrows(Exception.class, () -> authService.register(null));
	}

	@Test
	void register_MissingName() {
		RegisterRequest request = new RegisterRequest();
		request.setName(null);
		request.setEmail("test@example.com");
		request.setPassword("password123");

		assertThrows(Exception.class, () -> authService.register(request));
	}

	@Test
	void register_MissingEmail() {
		RegisterRequest request = new RegisterRequest();
		request.setName("Test User");
		request.setEmail(null);
		request.setPassword("password123");

		assertThrows(Exception.class, () -> authService.register(request));
	}

	@Test
	void register_MissingPassword() {
		RegisterRequest request = new RegisterRequest();
		request.setName("Test User");
		request.setEmail("test@example.com");
		request.setPassword(null);

		assertThrows(Exception.class, () -> authService.register(request));
	}

	@Test
	void login_Success() {
		AuthRequest request = new AuthRequest();
		request.setEmail("test@example.com");
		request.setPassword("password123");

		User user = new User();
		user.setId(1L);
		user.setEmail("test@example.com");
		user.setPassword("encodedPassword");
		user.setRole(UserRole.ROLE_USER);
		user.setFullName("Test User");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
		when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("token");

		AuthResponse response = authService.login(request);

		assertNotNull(response);
		assertEquals("token", response.getToken());
		assertEquals("ROLE_USER", response.getRole());
	}

	@Test
	void login_InvalidEmail() {
		AuthRequest request = new AuthRequest();
		request.setEmail("nonexistent@example.com");
		request.setPassword("password123");

		when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> authService.login(request));
	}

	@Test
	void login_InvalidPassword() {
		AuthRequest request = new AuthRequest();
		request.setEmail("test@example.com");
		request.setPassword("wrongpassword");

		User user = new User();
		user.setId(1L);
		user.setEmail("test@example.com");
		user.setPassword("encodedPassword");
		user.setRole(UserRole.ROLE_USER);

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

		assertThrows(Exception.class, () -> authService.login(request));
	}

	@Test
	void login_NullRequest() {
		assertThrows(Exception.class, () -> authService.login(null));
	}

	@Test
	void login_NullEmail() {
		AuthRequest request = new AuthRequest();
		request.setEmail(null);
		request.setPassword("password123");

		assertThrows(Exception.class, () -> authService.login(request));
	}

	@Test
	void login_NullPassword() {
		AuthRequest request = new AuthRequest();
		request.setEmail("test@example.com");
		request.setPassword(null);

		assertThrows(Exception.class, () -> authService.login(request));
	}
}