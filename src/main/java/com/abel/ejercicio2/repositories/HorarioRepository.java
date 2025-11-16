package com.abel.ejercicio2.repositories;

import com.abel.ejercicio2.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
}
