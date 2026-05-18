package com.example.traiviu_backend.dto.admin;

import jakarta.validation.constraints.NotBlank;

public record AdminClanUpdateRequest(
        @NotBlank String name,
        @NotBlank String status
) {}