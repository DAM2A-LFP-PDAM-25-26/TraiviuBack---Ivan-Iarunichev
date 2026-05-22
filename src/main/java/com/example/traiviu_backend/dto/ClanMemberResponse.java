package com.example.traiviu_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClanMemberResponse {
    private UUID userId;
    private String displayName;
    private String avatarUrl;
    private String role;
    private LocalDateTime joinedAt;
    private Boolean notificationsEnabled;

    public ClanMemberResponse() {}

    public ClanMemberResponse(UUID userId, String displayName, String avatarUrl, String role,
                              LocalDateTime joinedAt, Boolean notificationsEnabled) {
        this.userId = userId;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.joinedAt = joinedAt;
        this.notificationsEnabled = notificationsEnabled;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }

    public Boolean getNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(Boolean notificationsEnabled) { this.notificationsEnabled = notificationsEnabled; }
}