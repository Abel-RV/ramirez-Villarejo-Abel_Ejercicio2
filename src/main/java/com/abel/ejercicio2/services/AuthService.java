package com.abel.ejercicio2.services;

import com.abel.ejercicio2.dto.dtos.UsuarioDto;
import com.abel.ejercicio2.dto.request.LoginRequest;
import com.abel.ejercicio2.dto.request.RegisterRequest;
import com.abel.ejercicio2.entities.Usuario;
import com.abel.ejercicio2.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDto obtenerPerfil(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getRoles()
        );
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            // Autenticar al usuario con email y password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            // Si las credenciales son correctas, generar token
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error en el servidor"));
        }
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        try {
            // Verificar si el email ya existe
            if (usuarioRepository.findByEmail(registerRequest.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "El email ya está registrado"));
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(registerRequest.email());
            usuario.setPassword(passwordEncoder.encode(registerRequest.password()));  // Cifrar password
            usuario.setRoles("ROLE_PROFESOR");  // Rol por defecto

            usuario.setNombre(registerRequest.nombre());
            usuario.setApellidos(registerRequest.apellidos());

            usuarioRepository.save(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Usuario registrado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar usuario"));
        }
    }
}
