package com.example.demo.config;

import com.example.demo.model.Rol;
import com.example.demo.model.UsuarioSesion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/** Protege /admin/** (solo Admin) y /supervisor/** (solo Supervisor) usando la sesión HTTP. */
public class AutenticacionInterceptor implements HandlerInterceptor {

    public static final String USUARIO = "usuario";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UsuarioSesion usuario = (UsuarioSesion) request.getSession().getAttribute(USUARIO);
        String ruta = request.getRequestURI().substring(request.getContextPath().length());
        Rol requerido = ruta.startsWith("/admin") ? Rol.ADMIN : Rol.SUPERVISOR;

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        if (usuario.getRol() != requerido) {
            // Rol equivocado: lo devolvemos a su propia vista
            response.sendRedirect(request.getContextPath() + usuario.getRol().getInicio());
            return false;
        }
        return true;
    }
}
