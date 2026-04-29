package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.dto.UpdateProfileRequest;
import com.example.traiviu_backend.dto.auth.AuthResponse;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.UserRepository;
import com.example.traiviu_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMe(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("email", user.getEmail());
        data.put("displayName", user.getDisplayName());
        data.put("avatarUrl", user.getAvatarUrl());
        data.put("role", user.getRole());
        data.put("blocked", user.isBlocked());
        data.put("createdAt", user.getCreatedAt());
        data.put("lastLoginAt", user.getLastLoginAt());

        return ResponseEntity.ok(data);
    }

    @PutMapping("/me")
    public ResponseEntity<AuthResponse> updateMe(@Valid @RequestBody UpdateProfileRequest request,
                                                 Authentication authentication) {
        String currentEmail = authentication.getName();

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newEmail = request.getEmail().trim().toLowerCase();
        String newDisplayName = request.getDisplayName().trim();

        if (!user.getEmail().equalsIgnoreCase(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("El email ya está registrado");
        }

        user.setEmail(newEmail);
        user.setDisplayName(newDisplayName);

        User updatedUser = userRepository.save(user);

        AuthResponse response = authService.buildAuthResponse(updatedUser);

        return ResponseEntity.ok(response);
    }
}