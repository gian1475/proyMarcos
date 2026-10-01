package com.example.demo.controller;

import com.example.demo.model.EstadoBus;
import com.example.demo.model.EstadoViaje;
import com.example.demo.model.TipoServicio;
import com.example.demo.service.BusService;
import com.example.demo.service.ViajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    private final BusService busService;
    private final ViajeService viajeService;

    public ReporteController(BusService busService, ViajeService viajeService) {
        this.busService = busService;
        this.viajeService = viajeService;
    }

    @GetMapping("/grafico1")
    public String grafico1(Model model) {
        // Datos para gráfico de barras: Buses por tipo de servicio
        model.addAttribute("busEconomico", busService.contarPorServicio(TipoServicio.ECONOMICO));
        model.addAttribute("busConfort", busService.contarPorServicio(TipoServicio.CONFORT));
        model.addAttribute("busImperial", busService.contarPorServicio(TipoServicio.IMPERIAL));

        // Datos para gráfico lineal: Viajes por mes
        Map<String, Long> viajesPorMes = viajeService.contarViajesPorMes();
        model.addAttribute("meses", viajesPorMes.keySet());
        model.addAttribute("viajesPorMes", viajesPorMes.values());

        // Datos para gráfico de barras: Buses por estado
        model.addAttribute("busActivo", busService.contarPorEstado(EstadoBus.ACTIVO));
        model.addAttribute("busManten", busService.contarPorEstado(EstadoBus.MANTENIMIENTO));
        model.addAttribute("busInactivo", busService.contarPorEstado(EstadoBus.INACTIVO));

        return "reportes/grafico1";
    }

    @GetMapping("/grafico2")
    public String grafico2(Model model) {
        // Datos para gráfico circular: Viajes por estado
        model.addAttribute("vProgramado", viajeService.contarPorEstado(EstadoViaje.PROGRAMADO));
        model.addAttribute("vEnCurso", viajeService.contarPorEstado(EstadoViaje.EN_CURSO));
        model.addAttribute("vCompletado", viajeService.contarPorEstado(EstadoViaje.COMPLETADO));
        model.addAttribute("vCancelado", viajeService.contarPorEstado(EstadoViaje.CANCELADO));

        // Datos para gráfico de barras horizontal: Viajes por ruta
        Map<String, Long> viajesPorRuta = viajeService.contarViajesPorRuta();
        model.addAttribute("rutasLabels", viajesPorRuta.keySet());
        model.addAttribute("rutasData", viajesPorRuta.values());

        return "reportes/grafico2";
    }
}
