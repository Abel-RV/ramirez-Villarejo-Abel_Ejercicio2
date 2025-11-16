package com.abel.ejercicio2.services;

import com.abel.ejercicio2.beans.CopiarClase;
import com.abel.ejercicio2.dto.request.HorarioRequest;
import com.abel.ejercicio2.entities.Horario;
import com.abel.ejercicio2.repositories.HorarioRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class HorarioService {
    private final CopiarClase copiarClase= new CopiarClase();

    private final HorarioRepository horarioRepository;

    public List<Horario> obtenerTodosHorarios(){
        return horarioRepository.findAll();
    }

    public ResponseEntity<?> obtenerHorarioPorID(Long id){
        Optional<Horario> horario = horarioRepository.findById(id);
        return horario.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    public Horario guardarReserva(HorarioRequest horarioRequest){
        Horario horario= Horario.builder()
                .diasSemana(horarioRequest.diasSemana())
                .sesionDia(horarioRequest.sesionDia())
                .horarioInicio(horarioRequest.horarioInicio())
                .horarioFin(horarioRequest.horarioFin())
                .tramoHorario(horarioRequest.tramoHorario())
                .build();
        return horarioRepository.save(horario);
    }
    public ResponseEntity<Void> eliminar(Long id){
        Optional<Horario> horario = horarioRepository.findById(id);
        if(!horario.isPresent()){
            return ResponseEntity.notFound().build();
        }
        horarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
