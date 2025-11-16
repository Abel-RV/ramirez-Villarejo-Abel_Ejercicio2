package com.abel.ejercicio2.services;

import com.abel.ejercicio2.dto.dtos.UsuarioDto;
import com.abel.ejercicio2.dto.request.UsuarioRequest;
import com.abel.ejercicio2.entities.Usuario;
import com.abel.ejercicio2.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioDto actualizarUsuario(Long id, UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioRequest.nombre());
        usuario.setApellidos(usuarioRequest.apellidos());
        usuario.setEmail(usuarioRequest.email());

        Usuario usuActualizado = usuarioRepository.save(usuario);
        return new UsuarioDto(
                usuActualizado.getId(),
                usuActualizado.getNombre(),
                usuActualizado.getApellidos(),
                usuActualizado.getEmail(),
                usuActualizado.getRoles()
        );
    }

    public ResponseEntity<Void> eliminarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
