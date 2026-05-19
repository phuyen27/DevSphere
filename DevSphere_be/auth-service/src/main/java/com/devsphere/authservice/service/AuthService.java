package com.devsphere.authservice.service;

import com.devsphere.authservice.dto.AuthResponse;
import com.devsphere.authservice.dto.LoginRequest;
import com.devsphere.authservice.dto.RegisterRequest;
import com.devsphere.authservice.entity.User;
import com.devsphere.authservice.repository.UserRepository;
import com.devsphere.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService  jwtService;

    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(List.of("USER"))
                .createdAt(Instant.now())
                .userName(registerRequest.getUsername())
                .avatarUrl(registerRequest.getAvatarUrl())
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        boolean matches = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPasswordHash()
        );

        if(!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getId());
        return new AuthResponse(token);
    }
}
