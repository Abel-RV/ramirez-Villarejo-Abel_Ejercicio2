package com.abel.ejercicio2.entities;

import com.abel.ejercicio2.enums.DiasSemana;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Horario")
@Table(name = "TramoHorario")
@Builder
public class TramoHorario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DiasSemana diasSemana;
    private int sesionDia;

    private LocalTime horarioInicio;
    private LocalTime horarioFin;

    @ManyToOne
    @JsonIgnoreProperties("tramoHorario")
    private Reserva reserva;
}
