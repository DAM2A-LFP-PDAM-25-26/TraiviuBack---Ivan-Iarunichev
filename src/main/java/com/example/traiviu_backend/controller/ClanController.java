package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.dto.*;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.UserRepository;
import com.example.traiviu_backend.service.ClanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.traiviu_backend.dto.ClanMessageResponse;
import com.example.traiviu_backend.dto.SendClanMessageRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clans")
public class ClanController {

    private final ClanService clanService;
    private final UserRepository userRepository;

    public ClanController(ClanService clanService, UserRepository userRepository) {
        this.clanService = clanService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{clanId}/feed")
    public List<ClanFeedItemResponse> getClanFeed(@PathVariable UUID clanId) {
        UUID userId = getCurrentUserId();
        return clanService.getClanFeed(userId, clanId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClanResponse createClan(@Valid @RequestBody CreateClanRequest request) {
        UUID userId = getCurrentUserId();
        return clanService.createClan(userId, request);
    }

    @PostMapping("/join")
    public ClanResponse joinClan(@Valid @RequestBody JoinClanRequest request) {
        UUID userId = getCurrentUserId();
        return clanService.joinClan(userId, request);
    }

    @PostMapping("/{clanId}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveClan(@PathVariable UUID clanId) {
        UUID userId = getCurrentUserId();
        clanService.leaveClan(userId, clanId);
    }

    @GetMapping("/my")
    public List<ClanResponse> getMyClans() {
        UUID userId = getCurrentUserId();
        return clanService.getUserClans(userId);
    }

    @GetMapping("/{clanId}")
    public ClanResponse getClanById(@PathVariable UUID clanId) {
        UUID userId = getCurrentUserId();
        return clanService.getClanById(userId, clanId);
    }

    @PatchMapping("/{clanId}/notifications")
    public ClanResponse updateNotifications(
            @PathVariable UUID clanId,
            @Valid @RequestBody UpdateClanNotificationsRequest request
    ) {
        UUID userId = getCurrentUserId();
        return clanService.updateNotifications(userId, clanId, request);
    }

    private UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        return user.getId();
    }

    @PostMapping("/{clanId}/recommendations")
    @ResponseStatus(HttpStatus.CREATED)
    public void recommendToClan(
            @PathVariable UUID clanId,
            @Valid @RequestBody ClanRecommendationRequest request
    ) {
        UUID userId = getCurrentUserId();
        clanService.recommendToClan(userId, clanId, request);
    }

    @GetMapping("/{clanId}/messages")
    public List<ClanMessageResponse> getClanMessages(@PathVariable UUID clanId) {
        UUID userId = getCurrentUserId();
        return clanService.getClanMessages(userId, clanId);
    }

    @PostMapping("/{clanId}/messages")
    public ClanMessageResponse sendClanMessage(
            @PathVariable UUID clanId,
            @Valid @RequestBody SendClanMessageRequest request
    ) {
        UUID userId = getCurrentUserId();
        return clanService.sendMessage(userId, clanId, request);
    }
}