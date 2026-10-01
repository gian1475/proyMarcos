package com.example.demo.controller;

import com.example.demo.model.Bus;
import com.example.demo.model.EstadoBus;
import com.example.demo.model.TipoServicio;
import com.example.demo.service.BusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/buses")
public class BusController extends CrudController<Bus> {

    public BusController(BusService busService) {
        super(busService, "buses", "bus", "Bus", false);
    }

    @Override
    protected Bus nuevo() {
        return new Bus();
    }

    @Override
    protected void cargarCatalogos(Model model) {
        model.addAttribute("tiposServicio", TipoServicio.values());
        model.addAttribute("estadosBus", EstadoBus.values());
    }
}
