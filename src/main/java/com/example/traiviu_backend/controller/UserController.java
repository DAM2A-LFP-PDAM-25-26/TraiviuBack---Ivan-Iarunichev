package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8100")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String displayName = body.get("displayName");

        if (email == null || password == null || displayName == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Campos obligatorios"));
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "Email ya registrado"));
        }

        String hash = sha256(password);

        User user = User.builder()
                .email(email)
                .passwordHash(hash)
                .displayName(displayName)
                .build();

        userRepository.save(user);

        return ResponseEntity.status(201).body(Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "displayName", user.getDisplayName()
        ));
    }

    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
