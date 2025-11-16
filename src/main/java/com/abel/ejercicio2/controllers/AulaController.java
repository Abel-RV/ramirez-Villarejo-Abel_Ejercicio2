package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.dto.request.AulaRequest;
import com.abel.ejercicio2.entities.Aula;
import com.abel.ejercicio2.services.AulaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/aula")
@RestController
@AllArgsConstructor
public class AulaController {
    private final AulaService aulaService;


    @GetMapping
    public List<Aula> obtenerTodasAulas() {
        return aulaService.obtenerTodasAulas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAulaPorId(@PathVariable Long id) {

        return aulaService.obtenerAulaPorID(id);
    }


    @GetMapping("/{id}/reservas")
    public ResponseEntity<?> obtenerReservasPorAulaId(@PathVariable Long id) {
        return aulaService.obtenerReservasPorAula(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> putAula(@Valid @RequestBody AulaRequest aula, @PathVariable Long id) {
        return aulaService.actualizar(aula, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Aula postAula(@Valid @RequestBody AulaRequest aula) {
        return aulaService.guardarAula(aula);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarAula(@PathVariable Long id) {
        aulaService.eliminar(id);
    }
}
