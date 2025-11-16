package com.abel.ejercicio2.dto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.abel.ejercicio2.entities.Reserva}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReservaDTO(
        Long id,
        LocalDate fecha
        , String motivo,
        Integer numeroAsistentes,
        LocalDate fechaCreacion,
        AulaDTO aula,
        UsuarioDto usuario,
        List<HorarioDTO> horario){
}