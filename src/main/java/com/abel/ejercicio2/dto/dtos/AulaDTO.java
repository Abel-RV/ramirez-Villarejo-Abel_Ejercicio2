package com.abel.ejercicio2.dto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AulaDTO(
        Long id,
        String nombre,
        Integer capacidad,
        boolean esAulaOrdenadores,
        Integer numeroOrdenadores){
}