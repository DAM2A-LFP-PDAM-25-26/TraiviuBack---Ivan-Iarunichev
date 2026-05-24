package com.example.traiviu_backend.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminUserUpdateRequest(
        @NotBlank @Email String email,
        @NotBlank String displayName,
        @NotNull String role,
        @NotNull boolean blocked,
        String password
) {}