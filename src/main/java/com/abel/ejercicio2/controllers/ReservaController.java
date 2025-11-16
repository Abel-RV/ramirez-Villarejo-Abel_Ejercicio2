package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.dto.request.ReservaRequest;
import com.abel.ejercicio2.entities.Reserva;
import com.abel.ejercicio2.services.ReservaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reserva")
@RestController
@AllArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public List<Reserva> obtenerTodasReservas() {
        return reservaService.obtenerTodasReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorID(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> putReserva(@Valid @RequestBody ReservaRequest reserva, @PathVariable Long id) {
        return reservaService.actualizar(reserva, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Reserva postReserva(@Valid @RequestBody ReservaRequest reserva) {

        return reservaService.guardarReserva(reserva);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        return reservaService.eliminar(id);
    }
}
