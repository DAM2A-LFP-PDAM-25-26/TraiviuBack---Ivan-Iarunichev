package com.example.traiviu_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clan_messages")
public class ClanMessage {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "clanId", nullable = false)
    private UUID clanId;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "text", nullable = false, length = 1000)
    private String text;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public ClanMessage() {
    }

    public ClanMessage(UUID id, UUID clanId, UUID userId, String text, LocalDateTime createdAt) {
        this.id = id;
        this.clanId = clanId;
        this.userId = userId;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}