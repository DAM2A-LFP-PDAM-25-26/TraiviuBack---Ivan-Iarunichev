package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.ListEntity;
import com.example.traiviu_backend.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ListItemRepository extends JpaRepository<ListItem, UUID> {

    List<ListItem> findByList(ListEntity list);

    boolean existsByListAndTmdbIdAndMediaType(ListEntity list, Integer tmdbId, String mediaType);
}