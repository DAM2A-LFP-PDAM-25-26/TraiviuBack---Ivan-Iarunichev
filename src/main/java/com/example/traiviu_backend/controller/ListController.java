package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.model.ListEntity;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.ListRepository;
import com.example.traiviu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ListController {

    private final ListRepository listRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createList(@RequestBody Map<String, String> body) {
        String userIdStr = body.get("userId");
        String name = body.get("name");
        String type = body.getOrDefault("type", "CUSTOM");

        if (userIdStr == null || name == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "userId y name son obligatorios"));
        }

        UUID userId = UUID.fromString(userIdStr);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado"));
        }

        ListEntity list = ListEntity.builder()
                .user(user)
                .name(name)
                .type(type)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        listRepository.save(list);

        return ResponseEntity.status(201).body(list);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getListsByUser(@PathVariable UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado"));
        }

        List<ListEntity> lists = listRepository.findByUser(user);
        return ResponseEntity.ok(lists);
    }
}
