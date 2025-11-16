package com.abel.ejercicio2.dto.dtos;

import com.abel.ejercicio2.enums.DiasSemana;
import com.abel.ejercicio2.enums.TramoHorario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalTime;

/**
 * DTO for {@link com.abel.ejercicio2.entities.Horario}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HorarioDTO(
        Long id,
        DiasSemana diasSemana,
        int sesionDia,
        LocalTime horarioInicio,
        LocalTime horarioFin,
        TramoHorario tramoHorario) {
}