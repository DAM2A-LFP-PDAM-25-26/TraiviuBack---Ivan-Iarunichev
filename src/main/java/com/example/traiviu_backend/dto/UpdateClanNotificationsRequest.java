package com.example.traiviu_backend.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateClanNotificationsRequest {

    @NotNull(message = "El valor enabled es obligatorio")
    private Boolean enabled;

    public UpdateClanNotificationsRequest() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}