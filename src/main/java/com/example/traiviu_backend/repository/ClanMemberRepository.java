package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.ClanMember;
import com.example.traiviu_backend.model.ClanMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClanMemberRepository extends JpaRepository<ClanMember, ClanMemberId> {

    boolean existsByIdClanIdAndIdUserId(UUID clanId, UUID userId);
    Optional<ClanMember> findByIdClanIdAndIdUserId(UUID clanId, UUID userId);
    List<ClanMember> findByIdUserId(UUID userId);
    List<ClanMember> findByIdClanId(UUID clanId);

    @Modifying
    @Query("DELETE FROM ClanMember cm WHERE cm.id.clanId = :clanId")
    void deleteByClanId(@Param("clanId") UUID clanId);

    @Modifying
    @Query("DELETE FROM ClanMember cm WHERE cm.id.userId = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}