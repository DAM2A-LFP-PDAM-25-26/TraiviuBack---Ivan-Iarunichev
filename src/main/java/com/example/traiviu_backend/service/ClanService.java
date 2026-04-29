package com.example.traiviu_backend.service;

import com.example.traiviu_backend.dto.ClanResponse;
import com.example.traiviu_backend.dto.CreateClanRequest;
import com.example.traiviu_backend.dto.JoinClanRequest;
import com.example.traiviu_backend.dto.UpdateClanNotificationsRequest;
import com.example.traiviu_backend.model.Clan;
import com.example.traiviu_backend.model.ClanMember;
import com.example.traiviu_backend.model.ClanMemberId;
import com.example.traiviu_backend.model.enums.ClanStatus;
import com.example.traiviu_backend.repository.ClanMemberRepository;
import com.example.traiviu_backend.repository.ClanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClanService {

    private final ClanRepository clanRepository;
    private final ClanMemberRepository clanMemberRepository;

    public ClanService(ClanRepository clanRepository, ClanMemberRepository clanMemberRepository) {
        this.clanRepository = clanRepository;
        this.clanMemberRepository = clanMemberRepository;
    }

    @Transactional
    public ClanResponse createClan(UUID userId, CreateClanRequest request) {
        Clan clan = new Clan();
        clan.setId(UUID.randomUUID());
        clan.setName(request.getName().trim());
        clan.setInviteCode(generateInviteCode());
        clan.setOwnerId(userId);
        clan.setCreatedAt(LocalDateTime.now());
        clan.setStatus(ClanStatus.ACTIVE);
        clan.setMembersCount(1);

        Clan savedClan = clanRepository.save(clan);

        ClanMember member = new ClanMember();
        member.setId(new ClanMemberId(savedClan.getId(), userId));
        member.setRole("OWNER");
        member.setJoinedAt(LocalDateTime.now());
        member.setNotificationsEnabled(true);

        clanMemberRepository.save(member);

        return mapToResponse(savedClan, true);
    }

    @Transactional
    public ClanResponse joinClan(UUID userId, JoinClanRequest request) {
        Clan clan = clanRepository.findByInviteCodeAndStatus(
                        request.getInviteCode().trim().toUpperCase(),
                        ClanStatus.ACTIVE
                )
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        boolean alreadyMember = clanMemberRepository.existsByIdClanIdAndIdUserId(clan.getId(), userId);
        if (alreadyMember) {
            throw new RuntimeException("Ya perteneces a este clan");
        }

        ClanMember member = new ClanMember();
        member.setId(new ClanMemberId(clan.getId(), userId));
        member.setRole("MEMBER");
        member.setJoinedAt(LocalDateTime.now());
        member.setNotificationsEnabled(true);

        clanMemberRepository.save(member);

        clan.setMembersCount(clan.getMembersCount() + 1);
        Clan updatedClan = clanRepository.save(clan);

        return mapToResponse(updatedClan, true);
    }

    @Transactional
    public void leaveClan(UUID userId, UUID clanId) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        if (clan.getOwnerId().equals(userId)) {
            throw new RuntimeException("El creador del clan no puede salir sin transferir o eliminar el clan");
        }

        ClanMember member = clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        clanMemberRepository.delete(member);

        clan.setMembersCount(Math.max(0, clan.getMembersCount() - 1));
        clanRepository.save(clan);
    }

    @Transactional(readOnly = true)
    public List<ClanResponse> getUserClans(UUID userId) {
        List<ClanMember> memberships = clanMemberRepository.findByIdUserId(userId);

        return memberships.stream()
                .map(member -> {
                    Clan clan = clanRepository.findById(member.getId().getClanId())
                            .orElseThrow(() -> new RuntimeException("Clan no encontrado"));
                    return mapToResponse(clan, member.getNotificationsEnabled());
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ClanResponse getClanById(UUID userId, UUID clanId) {
        ClanMember member = clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        return mapToResponse(clan, member.getNotificationsEnabled());
    }

    @Transactional
    public ClanResponse updateNotifications(UUID userId, UUID clanId, UpdateClanNotificationsRequest request) {
        ClanMember member = clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        member.setNotificationsEnabled(request.getEnabled());
        clanMemberRepository.save(member);

        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        return mapToResponse(clan, member.getNotificationsEnabled());
    }

    private ClanResponse mapToResponse(Clan clan, Boolean notificationsEnabled) {
        return new ClanResponse(
                clan.getId(),
                clan.getName(),
                clan.getInviteCode(),
                clan.getOwnerId(),
                clan.getCreatedAt(),
                clan.getStatus().name(),
                clan.getMembersCount(),
                notificationsEnabled
        );
    }

    private String generateInviteCode() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }
}