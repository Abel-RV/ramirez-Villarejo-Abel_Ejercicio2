package com.abel.ejercicio2.services;

import com.abel.ejercicio2.beans.CopiarClase;
import com.abel.ejercicio2.entities.TramoHorario;
import com.abel.ejercicio2.repositories.HorarioRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HorarioService {
    private final CopiarClase copiarClase= new CopiarClase();

    private HorarioRepository horarioRepository;

    public List<TramoHorario> obtenerTodosHorarios(){
        return horarioRepository.findAll();
    }

    public TramoHorario obtenerReservaPorID(Long id){
        return horarioRepository.findById(id).orElse(null);
    }

    public TramoHorario guardarReserva(TramoHorario tramoHorario){
        return horarioRepository.save(tramoHorario);
    }
    public void eliminar(Long id){
        horarioRepository.deleteById(id);
    }

    @SneakyThrows
    public TramoHorario actualizar(TramoHorario tramoHorarioModificado, Long id){
        TramoHorario tramoHorario = obtenerReservaPorID(id);
        if(tramoHorario !=null){
            copiarClase.copyProperties(tramoHorario, tramoHorarioModificado);
            return horarioRepository.save(tramoHorario);
        }
        return tramoHorarioModificado;
    }
}
