package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/portal")
public class RegistroController {

    /** Longitud exacta por tipo de documento (tabla tipo_documento). */
    private static final Map<String, Integer> LONGITUD_DOCUMENTO = Map.of("DNI", 8, "CE", 9, "Pasaporte", 12);

    private final ClienteService clienteService;

    public RegistroController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/registro")
    public String formulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "portal/registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute Cliente cliente, @RequestParam String confirmarContrasena,
                            Model model, HttpSession session, RedirectAttributes redirect) {
        List<String> errores = validar(cliente, confirmarContrasena);
        if (!errores.isEmpty()) {
            cliente.setContrasena(null);
            model.addAttribute("errores", errores);
            return "portal/registro";
        }
        clienteService.registrar(cliente);
        session.setAttribute("clienteId", cliente.getId());
        redirect.addFlashAttribute("msgExito", "¡Cuenta creada! Bienvenido(a), " + cliente.getNombres() + ".");
        return "redirect:/portal/pago";
    }

    @GetMapping("/salir")
    public String salir(HttpSession session) {
        session.removeAttribute("clienteId");
        return "redirect:/portal/pago";
    }

    private List<String> validar(Cliente c, String confirmarContrasena) {
        List<String> errores = new ArrayList<>();
        if (vacio(c.getNombres()) || vacio(c.getApellidos())) {
            errores.add("Ingresa tus nombres y apellidos.");
        }
        Integer longitud = LONGITUD_DOCUMENTO.get(c.getTipoDocumento());
        String doc = c.getNumeroDocumento() == null ? "" : c.getNumeroDocumento().trim();
        if (longitud == null) {
            errores.add("Selecciona un tipo de documento válido.");
        } else if (doc.length() != longitud || ("DNI".equals(c.getTipoDocumento()) && !doc.matches("\\d+"))) {
            errores.add("El " + c.getTipoDocumento() + " debe tener exactamente " + longitud + " caracteres"
                    + ("DNI".equals(c.getTipoDocumento()) ? " numéricos." : "."));
        } else if (clienteService.existeDocumento(doc)) {
            errores.add("Ya existe una cuenta con ese número de documento.");
        }
        if (vacio(c.getTelefono()) || !c.getTelefono().trim().matches("\\d{9}")) {
            errores.add("El teléfono debe tener 9 dígitos.");
        }
        if (vacio(c.getCorreo()) || !c.getCorreo().trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            errores.add("Ingresa un correo electrónico válido.");
        } else if (clienteService.existeCorreo(c.getCorreo())) {
            errores.add("Ese correo ya está registrado.");
        }
        if (vacio(c.getContrasena()) || c.getContrasena().length() < 8) {
            errores.add("La contraseña debe tener al menos 8 caracteres.");
        } else if (!c.getContrasena().equals(confirmarContrasena)) {
            errores.add("Las contraseñas no coinciden.");
        }
        return errores;
    }

    private static boolean vacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
