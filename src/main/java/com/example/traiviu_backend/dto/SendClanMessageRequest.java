package com.example.traiviu_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendClanMessageRequest {

    @NotBlank
    @Size(max = 1000)
    private String content;

    public SendClanMessageRequest() {
    }

    public SendClanMessageRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}