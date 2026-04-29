package com.example.traiviu_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateClanRequest {

    @NotBlank(message = "El nombre del clan es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del clan debe tener entre 3 y 100 caracteres")
    private String name;

    public CreateClanRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}