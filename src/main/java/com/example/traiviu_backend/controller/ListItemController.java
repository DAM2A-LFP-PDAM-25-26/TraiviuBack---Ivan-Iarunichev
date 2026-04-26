package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.model.ListEntity;
import com.example.traiviu_backend.model.ListItem;
import com.example.traiviu_backend.repository.ListItemRepository;
import com.example.traiviu_backend.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/list-items")
@RequiredArgsConstructor
public class ListItemController {

    private final ListItemRepository listItemRepository;
    private final ListRepository listRepository;

    @GetMapping("/list/{listId}")
    public ResponseEntity<?> getItemsByList(@PathVariable UUID listId, Authentication authentication) {
        String email = authentication.getName();

        ListEntity list = listRepository.findById(listId).orElse(null);
        if (list == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Lista no encontrada"));
        }

        if (!list.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body(Map.of("error", "No puedes ver elementos de una lista ajena"));
        }

        List<ListItem> items = listItemRepository.findByListOrderByAddedAtDesc(list);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<?> addItemToList(@RequestBody Map<String, String> body, Authentication authentication) {
        String email = authentication.getName();

        String listIdStr = body.get("listId");
        String externalApiId = body.get("externalApiId");
        String title = body.get("title");
        String year = body.get("year");
        String posterUrl = body.get("posterUrl");

        if (listIdStr == null || externalApiId == null || title == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan datos obligatorios"));
        }

        ListEntity list = listRepository.findById(UUID.fromString(listIdStr)).orElse(null);
        if (list == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Lista no encontrada"));
        }

        if (!list.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body(Map.of("error", "No puedes añadir elementos a una lista ajena"));
        }

        ListItem newItem = ListItem.builder()
                .list(list)
                .externalApiId(externalApiId)
                .title(title)
                .year(year)
                .posterUrl(posterUrl)
                .addedAt(Instant.now())
                .build();

        listItemRepository.save(newItem);
        return ResponseEntity.status(201).body(newItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItemFromList(@PathVariable UUID itemId, Authentication authentication) {
        String email = authentication.getName();

        ListItem item = listItemRepository.findById(itemId).orElse(null);
        if (item == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Elemento no encontrado"));
        }

        if (!item.getList().getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body(Map.of("error", "No puedes borrar un elemento de una lista ajena"));
        }

        listItemRepository.delete(item);
        return ResponseEntity.ok(Map.of("message", "Elemento borrado de la lista"));
    }
}