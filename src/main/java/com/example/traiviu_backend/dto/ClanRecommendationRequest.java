package com.example.traiviu_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class ClanRecommendationRequest {

    @NotBlank
    private String externalApiId;

    @NotBlank
    private String title;

    private String year;
    private String posterUrl;

    @NotBlank
    private String mediaType;

    public String getExternalApiId() {
        return externalApiId;
    }

    public void setExternalApiId(String externalApiId) {
        this.externalApiId = externalApiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}