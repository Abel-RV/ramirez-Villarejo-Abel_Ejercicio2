package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.entities.Reserva;
import com.abel.ejercicio2.services.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> putReserva(@RequestBody Reserva reserva, @PathVariable Long id) {
        return reservaService.actualizar(reserva, id);
    }

    @PostMapping
    public Reserva postReserva(@RequestBody Reserva reserva) {
        return reservaService.guardarReserva(reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
    }
}
