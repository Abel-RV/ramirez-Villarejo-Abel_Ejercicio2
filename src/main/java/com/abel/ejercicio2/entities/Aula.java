package com.abel.ejercicio2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Aula")
@Table(name = "Aula")
@Builder
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private Integer capacidad;
    private boolean esAulaOrdenadores;
    private Integer numeroOrdenadores;

    @OneToMany(mappedBy = "aula",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnoreProperties("reservas")
    private List<Reserva> reservas;


}
