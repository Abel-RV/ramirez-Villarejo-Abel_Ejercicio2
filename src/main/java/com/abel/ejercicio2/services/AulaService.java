package com.abel.ejercicio2.services;

import com.abel.ejercicio2.beans.CopiarClase;
import com.abel.ejercicio2.entities.Aula;
import com.abel.ejercicio2.repositories.AulaRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AulaService {
    private final AulaRepository aulaRepository;
    private final CopiarClase copiarClase = new CopiarClase();

    public List<Aula> obtenerTodasAulas() {
        return aulaRepository.findAll();
    }

    public ResponseEntity<?> obtenerAulaPorID(Long id) {
        Optional<Aula> aula = aulaRepository.findById(id);
        return aula.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> obtenerReservasPorAula(Long id) {
        Optional<Aula> aula = aulaRepository.findById(id);
        return aula.map(a -> {
            if (a.getReservas() == null || a.getReservas().isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(a.getReservas());
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Aula guardarAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    public void eliminar(Long id) {
        aulaRepository.deleteById(id);
    }

    @SneakyThrows
    public ResponseEntity<Aula> actualizar(Aula aulaModificada, Long id) {
        Optional<Aula> aulaExistente = aulaRepository.findById(id);
        if (aulaExistente.isPresent()) {
            Aula aula = aulaExistente.get();
            copiarClase.copyProperties(aula, aulaModificada);
            Aula aulaActualizada = aulaRepository.save(aula);
            return ResponseEntity.ok(aulaActualizada);
        }
        return ResponseEntity.notFound().build();
    }
}
