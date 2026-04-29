package com.example.traiviu_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de usuario debe tener entre 2 y 100 caracteres")
    private String displayName;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    @Size(max = 255, message = "El correo electrónico no puede superar los 255 caracteres")
    private String email;
}