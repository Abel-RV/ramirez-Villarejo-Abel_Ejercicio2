package com.abel.ejercicio2.repositories;

import com.abel.ejercicio2.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {

}
