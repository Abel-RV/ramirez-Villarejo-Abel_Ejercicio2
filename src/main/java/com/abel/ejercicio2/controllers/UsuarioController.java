package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.dto.request.UsuarioRequest;
import com.abel.ejercicio2.entities.Usuario;
import com.abel.ejercicio2.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequest usuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id,usuario));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> BorrarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id);
    }
}
