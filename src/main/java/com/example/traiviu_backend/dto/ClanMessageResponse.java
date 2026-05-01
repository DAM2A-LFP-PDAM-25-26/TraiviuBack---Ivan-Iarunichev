package com.example.traiviu_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClanMessageResponse {

    private UUID id;
    private UUID clanId;
    private UUID userId;
    private String displayName;
    private String avatarUrl;
    private String content;
    private LocalDateTime createdAt;

    public ClanMessageResponse() {
    }

    public ClanMessageResponse(
            UUID id,
            UUID clanId,
            UUID userId,
            String displayName,
            String avatarUrl,
            String content,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.clanId = clanId;
        this.userId = userId;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getClanId() { return clanId; }
    public void setClanId(UUID clanId) { this.clanId = clanId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}