package com.example.traiviu_backend.model;

import com.example.traiviu_backend.model.ClanMemberId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "clan_members")
public class ClanMember {

    @EmbeddedId
    private ClanMemberId id;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "notifications_enabled", nullable = false)
    private Boolean notificationsEnabled;

    public ClanMember() {
    }

    public ClanMemberId getId() {
        return id;
    }

    public void setId(ClanMemberId id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}