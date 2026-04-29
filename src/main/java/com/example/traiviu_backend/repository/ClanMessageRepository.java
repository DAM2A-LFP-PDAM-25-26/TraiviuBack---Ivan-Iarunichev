package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.ClanMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClanMessageRepository extends JpaRepository<ClanMessage, UUID> {

    List<ClanMessage> findByClanIdOrderByCreatedAtAsc(UUID clanId);
}