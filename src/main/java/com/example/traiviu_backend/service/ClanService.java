package com.example.traiviu_backend.service;

import com.example.traiviu_backend.dto.*;
import com.example.traiviu_backend.model.Clan;
import com.example.traiviu_backend.model.ClanFeedEvent;
import com.example.traiviu_backend.model.ClanMember;
import com.example.traiviu_backend.model.ClanMemberId;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.model.enums.ClanStatus;
import com.example.traiviu_backend.model.enums.FeedEventType;
import com.example.traiviu_backend.model.enums.MediaType;
import com.example.traiviu_backend.repository.ClanFeedEventRepository;
import com.example.traiviu_backend.repository.ClanMemberRepository;
import com.example.traiviu_backend.repository.ClanRepository;
import com.example.traiviu_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.traiviu_backend.model.ClanMessage;
import com.example.traiviu_backend.repository.ClanMessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClanService {

    private final ClanRepository clanRepository;
    private final ClanMemberRepository clanMemberRepository;
    private final ClanFeedEventRepository clanFeedEventRepository;
    private final UserRepository userRepository;
    private final ClanMessageRepository clanMessageRepository;

    public ClanService(
            ClanRepository clanRepository,
            ClanMemberRepository clanMemberRepository,
            ClanFeedEventRepository clanFeedEventRepository,
            UserRepository userRepository,
            ClanMessageRepository clanMessageRepository
    ) {
        this.clanRepository = clanRepository;
        this.clanMemberRepository = clanMemberRepository;
        this.clanFeedEventRepository = clanFeedEventRepository;
        this.userRepository = userRepository;
        this.clanMessageRepository = clanMessageRepository;
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

        saveFeedEvent(savedClan.getId(), userId, FeedEventType.JOINED, null, null, null, null, null);

        return mapToResponse(savedClan, true);
    }

    @Transactional
    public ClanResponse joinClan(UUID userId, JoinClanRequest request) {
        String inviteCode = request.getInviteCode().trim().toUpperCase();

        Clan clan = clanRepository.findByInviteCodeAndStatus(inviteCode, ClanStatus.ACTIVE)
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

        saveFeedEvent(clan.getId(), userId, FeedEventType.JOINED, null, null, null, null, null);

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

        saveFeedEvent(clanId, userId, FeedEventType.LEFT, null, null, null, null, null);
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

    @Transactional(readOnly = true)
    public List<ClanFeedItemResponse> getClanFeed(UUID userId, UUID clanId) {
        clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        List<ClanFeedEvent> events = clanFeedEventRepository.findByClanIdOrderByCreatedAtDesc(clanId);

        return events.stream()
                .map(this::mapFeedEventToResponse)
                .toList();
    }

    private ClanFeedItemResponse mapFeedEventToResponse(ClanFeedEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String displayName = user.getDisplayName() != null && !user.getDisplayName().isBlank()
                ? user.getDisplayName()
                : user.getEmail();

        String title;
        switch (event.getType()) {
            case JOINED -> title = "se ha unido al clan";
            case LEFT -> title = "ha salido del clan";
            default -> title = event.getTitle();
        }

        return new ClanFeedItemResponse(
                event.getId(),
                displayName,
                user.getAvatarUrl(),
                event.getType().name(),
                title,
                event.getTmdbId(),
                event.getMediaType() != null ? event.getMediaType().name() : null,
                event.getYear(),
                null,
                event.getPosterUrl(),
                null,
                event.getCreatedAt()
        );
    }

    private void saveFeedEvent(
            UUID clanId,
            UUID userId,
            FeedEventType type,
            Integer tmdbId,
            MediaType mediaType,
            String title,
            Integer year,
            String posterUrl
    ) {
        ClanFeedEvent event = new ClanFeedEvent();
        event.setId(UUID.randomUUID());
        event.setClanId(clanId);
        event.setUserId(userId);
        event.setType(type);
        event.setTmdbId(tmdbId);
        event.setMediaType(mediaType);
        event.setTitle(title);
        event.setYear(year);
        event.setPosterUrl(posterUrl);
        event.setCreatedAt(LocalDateTime.now());

        clanFeedEventRepository.save(event);
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

    @Transactional
    public void recommendToClan(UUID userId, UUID clanId, ClanRecommendationRequest request) {
        clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        Integer tmdbId;
        try {
            tmdbId = Integer.valueOf(request.getExternalApiId());
        } catch (NumberFormatException e) {
            throw new RuntimeException("TMDB id no válido");
        }

        MediaType mediaType =
                "tv".equalsIgnoreCase(request.getMediaType())
                        ? MediaType.TV
                        : MediaType.MOVIE;

        Integer year = null;
        if (request.getYear() != null && !request.getYear().isBlank()) {
            try {
                year = Integer.valueOf(request.getYear());
            } catch (NumberFormatException e) {
                year = null;
            }
        }

        saveFeedEvent(
                clanId,
                userId,
                FeedEventType.RECOMMENDED,
                tmdbId,
                mediaType,
                request.getTitle(),
                year,
                request.getPosterUrl()
        );
    }

    @Transactional(readOnly = true)
    public List<ClanMessageResponse> getClanMessages(UUID userId, UUID clanId) {
        clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        List<ClanMessage> messages = clanMessageRepository.findByClanIdOrderByCreatedAtAsc(clanId);

        return messages.stream()
                .map(this::mapClanMessageToResponse)
                .toList();
    }

    @Transactional
    public ClanMessageResponse sendMessage(UUID userId, UUID clanId, SendClanMessageRequest request) {
        clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("No perteneces a este clan"));

        clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        String content = request.getContent() != null ? request.getContent().trim() : "";
        if (content.isBlank()) {
            throw new RuntimeException("El mensaje no puede estar vacío");
        }

        ClanMessage message = new ClanMessage();
        message.setId(UUID.randomUUID());
        message.setClanId(clanId);
        message.setUserId(userId);
        message.setText(content);
        message.setCreatedAt(LocalDateTime.now());

        ClanMessage saved = clanMessageRepository.save(message);

        return mapClanMessageToResponse(saved);
    }

    private ClanMessageResponse mapClanMessageToResponse(ClanMessage message) {
        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String displayName =
                user.getDisplayName() != null && !user.getDisplayName().isBlank()
                        ? user.getDisplayName()
                        : user.getEmail();

        return new ClanMessageResponse(
                message.getId(),
                message.getClanId(),
                message.getUserId(),
                displayName,
                user.getAvatarUrl(),
                message.getText(),
                message.getCreatedAt()
        );
    }
}