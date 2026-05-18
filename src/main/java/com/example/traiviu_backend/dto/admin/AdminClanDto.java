package com.example.traiviu_backend.dto.admin;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminClanDto(
        UUID id,
        String name,
        String inviteCode,
        UUID ownerId,
        LocalDateTime createdAt,
        String status,
        Integer membersCount
) {}