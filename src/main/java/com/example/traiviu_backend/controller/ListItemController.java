package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.model.ListEntity;
import com.example.traiviu_backend.model.ListItem;
import com.example.traiviu_backend.repository.ListItemRepository;
import com.example.traiviu_backend.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/list-items")
@CrossOrigin(origins = "http://localhost:8100")
@RequiredArgsConstructor
public class ListItemController {

    private final ListItemRepository listItemRepository;
    private final ListRepository listRepository;

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody Map<String, String> body) {
        String listIdStr = body.get("listId");
        String tmdbIdStr = body.get("tmdbId");
        String mediaType = body.get("mediaType"); // movie | tv

        if (listIdStr == null || tmdbIdStr == null || mediaType == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "listId, tmdbId y mediaType son obligatorios"));
        }

        UUID listId = UUID.fromString(listIdStr);
        Integer tmdbId = Integer.valueOf(tmdbIdStr);

        ListEntity list = listRepository.findById(listId).orElse(null);
        if (list == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Lista no encontrada"));
        }

        // Comprobación lógica antes de intentar guardar
        if (listItemRepository.existsByListAndTmdbIdAndMediaType(list, tmdbId, mediaType)) {
            return ResponseEntity.status(409)
                    .body(Map.of("error", "La película/serie ya está en la lista"));
        }

        try {
            ListItem item = ListItem.builder()
                    .list(list)
                    .tmdbId(tmdbId)
                    .mediaType(mediaType)
                    .addedAt(Instant.now())
                    .build();

            listItemRepository.save(item);
            return ResponseEntity.status(201).body(item);
        } catch (DataIntegrityViolationException ex) {
            // Por si entra una condición de carrera y la constraint salta igualmente
            return ResponseEntity.status(409)
                    .body(Map.of("error", "La película/serie ya está en la lista"));
        }
    }

    @GetMapping("/list/{listId}")
    public ResponseEntity<?> getItemsByList(@PathVariable UUID listId) {
        ListEntity list = listRepository.findById(listId).orElse(null);
        if (list == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Lista no encontrada"));
        }

        List<ListItem> items = listItemRepository.findByList(list);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID itemId) {
        if (!listItemRepository.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        listItemRepository.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }
}
