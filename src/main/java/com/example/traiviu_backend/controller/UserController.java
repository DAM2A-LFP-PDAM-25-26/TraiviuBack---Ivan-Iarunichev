package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.dto.UpdateProfileRequest;
import com.example.traiviu_backend.dto.auth.AuthResponse;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.UserRepository;
import com.example.traiviu_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<AuthResponse> updateMe(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication
    ) {
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

    @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadAvatar(
            @RequestPart("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No se ha enviado ninguna imagen");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String originalFilename = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "avatar.jpg";

        String safeFilename = UUID.randomUUID() + "-" + originalFilename.replaceAll("\\s+", "_");

        Path uploadDir = Paths.get("uploads/avatars");
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(safeFilename);
        Files.write(filePath, file.getBytes());

        String avatarUrl = "/uploads/avatars/" + safeFilename;

        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);

        Map<String, Object> data = new HashMap<>();
        data.put("avatarUrl", avatarUrl);

        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/me/avatar")
    public ResponseEntity<Map<String, Object>> removeAvatar(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setAvatarUrl(null);
        userRepository.save(user);

        Map<String, Object> data = new HashMap<>();
        data.put("avatarUrl", null);

        return ResponseEntity.ok(data);
    }
}