package com.example.demo.controller;

import com.example.demo.model.Ruta;
import com.example.demo.service.RutaService;
import com.example.demo.service.TerminalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/rutas")
public class RutaController extends CrudController<Ruta> {

    private final TerminalService terminalService;

    public RutaController(RutaService rutaService, TerminalService terminalService) {
        super(rutaService, "rutas", "ruta", "Ruta", true);
        this.terminalService = terminalService;
    }

    @Override
    protected Ruta nuevo() {
        return new Ruta();
    }

    @Override
    protected void cargarCatalogos(Model model) {
        model.addAttribute("terminales", terminalService.listarTodos());
    }
}
