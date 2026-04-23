package com.hotel.user;

import com.hotel.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public AuthResponse register(RegisterRequest request) {
		if (request == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
		}
		
		String name = (request.getName() != null) ? request.getName().trim() : "";
		String email = (request.getEmail() != null) ? request.getEmail().trim().toLowerCase() : "";
		String password = (request.getPassword() != null) ? request.getPassword() : "";
		
		if (name.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
		}
		if (email.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
		}
		if (password.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
		}

		if (userRepository.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
		}

		User user = new User();
		user.setFullName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(UserRole.ROLE_USER);
		User savedUser = userRepository.save(user);

		String token = jwtUtil.generateToken(String.valueOf(savedUser.getId()), savedUser.getRole().name());
		return AuthResponse.from(savedUser, token);
	}

	@Transactional(readOnly = true)
	public AuthResponse login(AuthRequest request) {
		String email = (request != null && request.getEmail() != null) 
			? request.getEmail().trim().toLowerCase() 
			: "";
		String password = (request != null && request.getPassword() != null) 
			? request.getPassword() 
			: "";
			
		if (email.isEmpty() || password.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
		}
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
		}

		String token = jwtUtil.generateToken(String.valueOf(user.getId()), user.getRole().name());
		return AuthResponse.from(user, token);
	}

}
