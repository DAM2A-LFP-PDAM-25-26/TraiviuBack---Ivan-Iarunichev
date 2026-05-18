package com.example.traiviu_backend.dto.admin;

import java.time.Instant;
import java.util.UUID;

public record AdminUserDto(
        UUID id,
        String email,
        String displayName,
        String avatarUrl,
        String role,
        boolean blocked,
        Instant createdAt,
        Instant lastLoginAt
) {}