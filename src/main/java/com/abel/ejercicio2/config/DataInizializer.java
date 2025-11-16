package com.abel.ejercicio2.config;

import com.abel.ejercicio2.entities.Usuario;
import com.abel.ejercicio2.repositories.UsuarioRepository;
import com.abel.ejercicio2.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInizializer {
    private final PasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init(){
        Usuario usuario = Usuario.builder()
                .nombre("admin")
                .apellidos("admin")
                .email("admin@gmail.com")
                .password(encoder.encode("admin1234"))
                .roles("ROLE_ADMIN")
                .build();

        usuarioRepository.save(usuario);
    }
}
