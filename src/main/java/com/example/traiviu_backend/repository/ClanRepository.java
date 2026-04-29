package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.Clan;
import com.example.traiviu_backend.model.enums.ClanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClanRepository extends JpaRepository<Clan, UUID> {

    Optional<Clan> findByInviteCodeAndStatus(String inviteCode, ClanStatus status);
}