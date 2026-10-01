package com.example.demo.config;

import com.example.demo.model.UsuarioSesion;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Expone el usuario de la sesión a todas las vistas como ${usuario}. */
@ControllerAdvice
public class UsuarioAdvice {

    @ModelAttribute("usuario")
    public UsuarioSesion usuario(HttpSession session) {
        return (UsuarioSesion) session.getAttribute(AutenticacionInterceptor.USUARIO);
    }
}
