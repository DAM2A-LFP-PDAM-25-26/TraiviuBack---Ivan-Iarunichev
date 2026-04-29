package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.ClanFeedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClanFeedEventRepository extends JpaRepository<ClanFeedEvent, UUID> {

    List<ClanFeedEvent> findByClanIdOrderByCreatedAtDesc(UUID clanId);
}