package com.example.traiviu_backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private String userId;
    private String email;
    private String displayName;
    private String role;
}