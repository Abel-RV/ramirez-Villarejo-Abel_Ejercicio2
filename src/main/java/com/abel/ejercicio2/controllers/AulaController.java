package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.entities.Aula;
import com.abel.ejercicio2.services.AulaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<Aula> putAula(@RequestBody Aula aula, @PathVariable Long id) {
        return aulaService.actualizar(aula, id);
    }

    @PostMapping
    public Aula postAula(@RequestBody Aula aula) {
        return aulaService.guardarAula(aula);
    }

    @DeleteMapping("/{id}")
    public void eliminarAula(@PathVariable Long id) {
        aulaService.eliminar(id);
    }
}
