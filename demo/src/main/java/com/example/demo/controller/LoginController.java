package com.example.demo.controller;

import com.example.demo.config.AutenticacionInterceptor;
import com.example.demo.model.Rol;
import com.example.demo.model.UsuarioSesion;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        UsuarioSesion actual = (UsuarioSesion) session.getAttribute(AutenticacionInterceptor.USUARIO);
        return actual != null ? "redirect:" + actual.getRol().getInicio() : "login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String correo, @RequestParam String clave,
                                @RequestParam Rol rol, HttpSession session) {
        Optional<UsuarioSesion> usuario = authService.autenticar(correo, clave, rol);
        if (usuario.isEmpty()) {
            return "redirect:/login?error";
        }
        session.setAttribute(AutenticacionInterceptor.USUARIO, usuario.get());
        return "redirect:" + rol.getInicio();
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
