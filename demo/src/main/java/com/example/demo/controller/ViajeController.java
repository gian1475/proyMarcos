package com.example.demo.controller;

import com.example.demo.model.EstadoViaje;
import com.example.demo.model.Viaje;
import com.example.demo.service.BusService;
import com.example.demo.service.ChoferService;
import com.example.demo.service.RutaService;
import com.example.demo.service.ViajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/viajes")
public class ViajeController extends CrudController<Viaje> {

    private final RutaService rutaService;
    private final BusService busService;
    private final ChoferService choferService;

    public ViajeController(ViajeService viajeService, RutaService rutaService,
                           BusService busService, ChoferService choferService) {
        super(viajeService, "viajes", "viaje", "Viaje", false);
        this.rutaService = rutaService;
        this.busService = busService;
        this.choferService = choferService;
    }

    @Override
    protected Viaje nuevo() {
        return new Viaje();
    }

    @Override
    protected void cargarCatalogos(Model model) {
        model.addAttribute("rutas", rutaService.listarTodos());
        model.addAttribute("buses", busService.listarTodos());
        model.addAttribute("choferes", choferService.listarTodos());
        model.addAttribute("estadosViaje", EstadoViaje.values());
    }
}
