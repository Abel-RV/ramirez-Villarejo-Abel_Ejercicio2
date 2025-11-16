package com.abel.ejercicio2.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.abel.ejercicio2.entities.Usuario}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioRequest(
        @NotNull(message = "El nombre no puede estar vacio") @Email String nombre,
        @NotNull(message = "El apellido no puede estar vacio") String apellidos,
        @Email(message = "El email no tiene el formato correcto")
        @NotBlank(message = "El email es obligatorio") String email){
}