package com.hotel.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminBootstrapConfig {

	@Bean
	CommandLineRunner adminBootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.countByRole(UserRole.ROLE_ADMIN) > 0) {
				return;
			}

			User admin = new User();
			admin.setFullName("System Admin");
			admin.setEmail("admin@stayease.com");
			admin.setPassword(passwordEncoder.encode("Admin@123"));
			admin.setRole(UserRole.ROLE_ADMIN);
			userRepository.save(admin);
		};
	}

}
