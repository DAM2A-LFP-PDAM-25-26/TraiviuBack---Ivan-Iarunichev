package com.example.traiviu_backend.dto.admin;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminClanMemberDto(
        UUID userId,
        String email,
        String displayName,
        String role,
        LocalDateTime joinedAt
) {}