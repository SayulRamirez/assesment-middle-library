package com.gateway.library.service;

import com.gateway.library.dao.AuthResponse;
import com.gateway.library.dao.LoginRequest;
import com.gateway.library.dao.RegisterRequest;
import com.gateway.library.entity.User;
import com.gateway.library.exception.UserNotAuthenticateException;
import com.gateway.library.repository.UserRepository;
import com.gateway.library.util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public Optional<AuthResponse> login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotAuthenticateException("Credenciales incorrectas"));

        boolean authenticate = passwordEncoder.matches(request.password(), user.getPassword());

        return authenticate ?
                Optional.of(new AuthResponse(jwtUtil.generateToken(user.getUsername()))) : Optional.empty();
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username()))
            throw new EntityExistsException("El correo ya esta registrado");

        userRepository.save(User.builder()
                .names(request.names())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role("READER")
                .build());
    }
}
