package com.abel.ejercicio2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Reserva")
@Table(name = "Reserva")
@Builder

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate fecha;
    private String motivo;
    private Integer numeroAsistentes;

    @CreationTimestamp
    @Column
    private LocalDate fechaCreacion;

    @ManyToOne
    @JsonIgnoreProperties("reservas")
    private Aula aula;

    @ManyToOne
    @JsonIgnoreProperties("reservas")
    private Usuario usuario;

    @OneToMany(mappedBy = "reserva",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnoreProperties("reserva")
    private List<TramoHorario> tramoHorario;
}
