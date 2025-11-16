package com.abel.ejercicio2.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AulaRequest(
        @NotBlank String nombre,
        @NotNull Integer capacidad,
        @NotNull boolean esAulaOrdenadores,
        @NotNull Integer numeroOrdenadores) {
}