package com.gateway.library.controller;

import com.gateway.library.dao.AuthResponse;
import com.gateway.library.dao.LoginRequest;
import com.gateway.library.dao.RegisterRequest;
import com.gateway.library.service.AuthService;
import com.gateway.library.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        if ("admin".equals(request.username()) && "password".equals(request.password())) {
            String token = jwtUtil.generateToken(request.username());

            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);

        return ResponseEntity.ok().build();
    }
}
