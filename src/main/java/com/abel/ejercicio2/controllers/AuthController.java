package com.abel.ejercicio2.controllers;

import com.abel.ejercicio2.dto.dtos.UsuarioDto;
import com.abel.ejercicio2.dto.request.LoginRequest;
import com.abel.ejercicio2.dto.request.RegisterRequest;
import com.abel.ejercicio2.entities.Usuario;
import com.abel.ejercicio2.repositories.UsuarioRepository;
import com.abel.ejercicio2.services.AuthService;
import com.abel.ejercicio2.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor // Crea un constructor que Inyecta todos los objetos "final"
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(Authentication authentication) {
        return ResponseEntity.ok(authService.obtenerPerfil(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
       return  authService.login(loginRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
       return ResponseEntity.ok(authService.register(registerRequest));
    }
}
