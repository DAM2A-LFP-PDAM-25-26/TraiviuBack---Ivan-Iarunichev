package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.ClanMember;
import com.example.traiviu_backend.model.ClanMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClanMemberRepository extends JpaRepository<ClanMember, ClanMemberId> {

    boolean existsByIdClanIdAndIdUserId(UUID clanId, UUID userId);

    Optional<ClanMember> findByIdClanIdAndIdUserId(UUID clanId, UUID userId);

    List<ClanMember> findByIdUserId(UUID userId);
}