package com.abel.ejercicio2.services;

import com.abel.ejercicio2.beans.CopiarClase;
import com.abel.ejercicio2.dto.request.ReservaRequest;
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

    public Reserva guardarReserva(ReservaRequest reservaRequest) {
        Reserva reserva = Reserva.builder()
                .build();
        return reservaRepository.save(reserva);
    }

    public ResponseEntity<Void> eliminar(Long id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if(!reservaOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @SneakyThrows
    public ResponseEntity<Reserva> actualizar(ReservaRequest reservaModificada, Long id) {
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        if (!reservaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Reserva reserva = reservaExistente.get();
        copiarClase.copyProperties(reserva, reservaModificada);
        Reserva reservaActualizada = reservaRepository.save(reserva);
        return ResponseEntity.ok(reservaActualizada);

    }
}
