package com.example.demo.service;

import com.example.demo.model.Rol;
import com.example.demo.model.UsuarioSesion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Autenticación sencilla con usuarios fijos en memoria.
 * Al integrar MySQL solo hay que cambiar {@link #USUARIOS} por una consulta a las tablas usuarios/roles.
 */
@Service
public class AuthService {

    private static final List<UsuarioSesion> USUARIOS = List.of(
            new UsuarioSesion("Admin", "admin@loschaskis.com", "12345678", Rol.ADMIN, "Todas las sedes"),
            new UsuarioSesion("Juan Pérez", "jperez@loschaskis.com", "12345678", Rol.SUPERVISOR, "Terminal Piura"));

    public Optional<UsuarioSesion> autenticar(String correo, String clave, Rol rol) {
        if (correo == null || clave == null || rol == null) {
            return Optional.empty();
        }
        return USUARIOS.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo.trim())
                        && u.getClave().equals(clave)
                        && u.getRol() == rol)
                .findFirst();
    }
}
