package com.example.traiviu_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClanResponse {

    private UUID id;
    private String name;
    private String inviteCode;
    private UUID ownerId;
    private LocalDateTime createdAt;
    private String status;
    private Integer membersCount;
    private Boolean notificationsEnabled;

    public ClanResponse() {
    }

    public ClanResponse(UUID id, String name, String inviteCode, UUID ownerId,
                        LocalDateTime createdAt, String status, Integer membersCount,
                        Boolean notificationsEnabled) {
        this.id = id;
        this.name = name;
        this.inviteCode = inviteCode;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.status = status;
        this.membersCount = membersCount;
        this.notificationsEnabled = notificationsEnabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}