package com.abel.ejercicio2.dto.request;

import com.abel.ejercicio2.entities.Aula;
import com.abel.ejercicio2.entities.Horario;
import com.abel.ejercicio2.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ReservaRequest(
        @NotNull @FutureOrPresent LocalDate fecha,
        @NotBlank String motivo,
        @NotNull Integer numeroAsistentes,
        Long aulaId,
        Long usuarioId,
        List<Long> horarioId){
}