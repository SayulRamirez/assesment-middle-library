package com.gateway.library;

import com.gateway.library.entity.User;
import com.gateway.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GatewayLibraryApplication {

	@Value("${init.names}")
	private String names;

	@Value("${init.username}")
	private String username;

	@Value("${init.password}")
	private String password;

	@Value("${init.role}")
	private String role;

	public static void main(String[] args) {
		SpringApplication.run(GatewayLibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (!userRepository.existsByUsername(username)) {
				User user = User.builder()
						.names(names)
						.username(username)
						.password(passwordEncoder.encode(password))
						.role(role)
						.build();

				userRepository.save(user);
				System.out.println("Usuario inicial creado: " + username);
			}
		};
	}
}
