package com.example.traiviu_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClanFeedItemResponse {

    private UUID id;
    private String userDisplayName;
    private String userProfileImageUrl;
    private String action;
    private String title;
    private Integer tmdbId;
    private String mediaType;
    private Integer year;
    private Double rating;
    private String posterUrl;
    private String listName;
    private LocalDateTime createdAt;

    public ClanFeedItemResponse() {
    }

    public ClanFeedItemResponse(
            UUID id,
            String userDisplayName,
            String userProfileImageUrl,
            String action,
            String title,
            Integer tmdbId,
            String mediaType,
            Integer year,
            Double rating,
            String posterUrl,
            String listName,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userDisplayName = userDisplayName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.action = action;
        this.title = title;
        this.tmdbId = tmdbId;
        this.mediaType = mediaType;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.listName = listName;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Integer tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}