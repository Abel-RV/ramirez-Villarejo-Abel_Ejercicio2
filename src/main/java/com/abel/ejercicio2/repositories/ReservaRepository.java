package com.abel.ejercicio2.repositories;

import com.abel.ejercicio2.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
}
