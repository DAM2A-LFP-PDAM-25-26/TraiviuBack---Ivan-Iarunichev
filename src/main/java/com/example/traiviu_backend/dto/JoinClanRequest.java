package com.example.traiviu_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JoinClanRequest {

    @NotBlank(message = "El código de invitación es obligatorio")
    @Size(min = 6, max = 20, message = "El código de invitación no es válido")
    private String inviteCode;

    public JoinClanRequest() {
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}