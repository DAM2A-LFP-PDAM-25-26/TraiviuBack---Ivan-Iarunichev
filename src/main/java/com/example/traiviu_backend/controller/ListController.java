package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.model.ListEntity;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.ListRepository;
import com.example.traiviu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> createList(@RequestBody Map<String, String> body, Authentication authentication) {
        String name = body.get("name");
        String type = body.getOrDefault("type", "CUSTOM");

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "name es obligatorio"));
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ListEntity list = ListEntity.builder()
                .user(user)
                .name(name.trim())
                .type(type)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        listRepository.save(list);

        return ResponseEntity.status(201).body(list);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyLists(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<ListEntity> lists = listRepository.findByUser(user);
        return ResponseEntity.ok(lists);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable UUID listId, Authentication authentication) {
        String email = authentication.getName();

        ListEntity list = listRepository.findById(listId).orElse(null);
        if (list == null) {
            return ResponseEntity.status(404).body(Map.of("error", "La lista no existe"));
        }

        if (!list.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body(Map.of("error", "No puedes borrar una lista ajena"));
        }

        listRepository.delete(list);
        return ResponseEntity.ok(Map.of("message", "Lista eliminada"));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<?> updateList(
            @PathVariable UUID listId,
            @RequestBody Map<String, String> body,
            Authentication authentication
    ) {
        String email = authentication.getName();

        ListEntity list = listRepository.findById(listId).orElse(null);
        if (list == null) {
            return ResponseEntity.status(404).body(Map.of("error", "La lista no existe"));
        }

        if (!list.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body(Map.of("error", "No puedes editar una lista ajena"));
        }

        String newName = body.get("name");
        if (newName == null || newName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre es obligatorio"));
        }

        list.setName(newName.trim());
        list.setUpdatedAt(Instant.now());
        listRepository.save(list);

        return ResponseEntity.ok(list);
    }
}