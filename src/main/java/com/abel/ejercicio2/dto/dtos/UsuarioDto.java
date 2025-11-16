package com.abel.ejercicio2.dto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioDto(
        Long id,
        String nombre,
        String apellidos,
        @Email(message = "El email no tiene el formato correcto")
        @NotBlank(message = "El email es obligatorio") String email,
        String roles) {
}