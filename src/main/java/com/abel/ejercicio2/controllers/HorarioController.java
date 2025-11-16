package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.dto.request.HorarioRequest;
import com.abel.ejercicio2.entities.Horario;
import com.abel.ejercicio2.repositories.HorarioRepository;
import com.abel.ejercicio2.services.HorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tramo-horario")
public class HorarioController {
    private final HorarioService horarioService;

    @GetMapping
    public List<Horario> getHorarios(){
        return horarioService.obtenerTodosHorarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHorario(@PathVariable Long id){
        return horarioService.obtenerHorarioPorID(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHorario(@PathVariable Long id){
        return horarioService.eliminar(id);
    }
    @PostMapping("/{id}")
    public Horario guardarHorario(@Valid @RequestBody HorarioRequest horario){
        return horarioService.guardarReserva(horario);
    }
}
