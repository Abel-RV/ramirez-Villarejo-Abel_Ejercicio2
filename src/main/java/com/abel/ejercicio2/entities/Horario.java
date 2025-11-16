package com.abel.ejercicio2.entities;

import com.abel.ejercicio2.enums.DiasSemana;
import com.abel.ejercicio2.enums.TramoHorario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Horario")
@Table(name = "Horario")
@Builder
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DiasSemana diasSemana;
    private int sesionDia;

    private LocalTime horarioInicio;
    private LocalTime horarioFin;

    private TramoHorario tramoHorario;



    @ManyToOne
    @JsonIgnoreProperties("horario")
    private Reserva reserva;
}
