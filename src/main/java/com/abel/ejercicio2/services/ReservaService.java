package com.abel.ejercicio2.services;

import com.abel.ejercicio2.beans.CopiarClase;
import com.abel.ejercicio2.entities.Reserva;
import com.abel.ejercicio2.repositories.ReservaRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservaService {
    private final CopiarClase copiarClase = new CopiarClase();
    private final ReservaRepository reservaRepository;

    public List<Reserva> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    public ResponseEntity<?> obtenerReservaPorID(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }

    @SneakyThrows
    public ResponseEntity<Reserva> actualizar(Reserva reservaModificada, Long id) {
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        if (reservaExistente.isPresent()) {
            Reserva reserva = reservaExistente.get();
            copiarClase.copyProperties(reserva, reservaModificada);
            Reserva reservaActualizada = reservaRepository.save(reserva);
            return ResponseEntity.ok(reservaActualizada);
        }
        return ResponseEntity.notFound().build();
    }
}
