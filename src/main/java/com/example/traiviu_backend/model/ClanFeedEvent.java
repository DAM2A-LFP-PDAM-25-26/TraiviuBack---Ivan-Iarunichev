package com.example.traiviu_backend.model;

import com.example.traiviu_backend.model.enums.FeedEventType;
import com.example.traiviu_backend.model.enums.MediaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clan_feed")
public class ClanFeedEvent {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "clanId", nullable = false)
    private UUID clanId;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private FeedEventType type;

    @Column(name = "tmdbId", nullable = false)
    private Integer tmdbId;

    @Enumerated(EnumType.STRING)
    @Column(name = "mediaType", nullable = false, length = 20)
    private MediaType mediaType;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public ClanFeedEvent() {
    }

    public ClanFeedEvent(UUID id, UUID clanId, UUID userId, FeedEventType type, Integer tmdbId, MediaType mediaType, LocalDateTime createdAt) {
        this.id = id;
        this.clanId = clanId;
        this.userId = userId;
        this.type = type;
        this.tmdbId = tmdbId;
        this.mediaType = mediaType;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClanId() {
        return clanId;
    }

    public void setClanId(UUID clanId) {
        this.clanId = clanId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public FeedEventType getType() {
        return type;
    }

    public void setType(FeedEventType type) {
        this.type = type;
    }

    public Integer getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Integer tmdbId) {
        this.tmdbId = tmdbId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}