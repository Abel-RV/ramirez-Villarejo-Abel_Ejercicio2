package com.abel.ejercicio2.repositories;

import com.abel.ejercicio2.entities.TramoHorario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<TramoHorario, Long> {
}
