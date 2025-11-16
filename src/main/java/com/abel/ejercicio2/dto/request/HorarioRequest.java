package com.abel.ejercicio2.dto.request;

import com.abel.ejercicio2.enums.DiasSemana;
import com.abel.ejercicio2.enums.TramoHorario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

/**
 * DTO for {@link com.abel.ejercicio2.entities.Horario}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HorarioRequest(
        @NotNull DiasSemana diasSemana,
        int sesionDia,
        @NotNull LocalTime horarioInicio,
        @NotNull LocalTime horarioFin,
        @NotNull TramoHorario tramoHorario) {
}