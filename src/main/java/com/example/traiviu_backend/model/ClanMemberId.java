package com.example.traiviu_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ClanMemberId implements Serializable {

    @Column(name = "clan_id", nullable = false)
    private UUID clanId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public ClanMemberId() {
    }

    public ClanMemberId(UUID clanId, UUID userId) {
        this.clanId = clanId;
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClanMemberId that = (ClanMemberId) o;
        return Objects.equals(clanId, that.clanId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clanId, userId);
    }
}