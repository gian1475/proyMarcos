package com.example.demo.controller;

import com.example.demo.service.BusService;
import com.example.demo.service.ChoferService;
import com.example.demo.service.ClienteService;
import com.example.demo.service.RutaService;
import com.example.demo.service.TerminalService;
import com.example.demo.service.ViajeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    /** Colores de la paleta para los gráficos: Azul Profundo, Dorado Andino, Gris Plata. */
    private static final List<String> COLORES_GRAFICO = List.of("#0D1B2A", "#D4AF37", "#8C8C8C");

    private final TerminalService terminalService;
    private final BusService busService;
    private final ChoferService choferService;
    private final RutaService rutaService;
    private final ViajeService viajeService;
    private final ClienteService clienteService;

    public HomeController(TerminalService terminalService, BusService busService,
                          ChoferService choferService, RutaService rutaService,
                          ViajeService viajeService, ClienteService clienteService) {
        this.terminalService = terminalService;
        this.busService = busService;
        this.choferService = choferService;
        this.rutaService = rutaService;
        this.viajeService = viajeService;
        this.clienteService = clienteService;
    }

    @GetMapping({"/", "/inicio", "/portal"})
    public String inicio(Model model) {
        model.addAttribute("terminales", terminalService.listarTodos());
        model.addAttribute("rutas", rutaService.listarTodos());
        model.addAttribute("viajes", viajeService.listarTodos());
        return "inicio";
    }

    @GetMapping({"/admin", "/admin/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("terminales", terminalService.listarTodos());
        model.addAttribute("totalBuses", busService.listarTodos().size());
        model.addAttribute("totalChoferes", choferService.listarTodos().size());
        model.addAttribute("totalViajes", viajeService.listarTodos().size());

        // KPIs (valores de referencia hasta conectar ventas en MySQL)
        model.addAttribute("ingresosTotales", "256,780.50");
        model.addAttribute("pasajesVendidos", "2,842");
        model.addAttribute("ocupacionPromedio", "84.7");
        model.addAttribute("rutaMasVendida", "LIMA → PIURA");
        model.addAttribute("pasajesRutaTop", "1,245");

        Map<String, Double> ventasPorMetodo = new LinkedHashMap<>();
        ventasPorMetodo.put("Yape", 98540.00);
        ventasPorMetodo.put("Plin", 72315.00);
        ventasPorMetodo.put("Tarjeta de Crédito/Débito", 85925.50);

        Map<String, Integer> comprobantes = new LinkedHashMap<>();
        comprobantes.put("Boletas", 1932);
        comprobantes.put("Facturas", 910);

        model.addAttribute("ventasPorMetodo", ventasPorMetodo);
        model.addAttribute("comprobantes", comprobantes);
        model.addAttribute("totalComprobantes", comprobantes.values().stream().mapToInt(Integer::intValue).sum());
        model.addAttribute("coloresGrafico", COLORES_GRAFICO);
        return "dashboard";
    }

    @GetMapping("/portal/buscar")
    public String portalBuscar(Model model) {
        model.addAttribute("viajes", viajeService.listarTodos());
        model.addAttribute("terminales", terminalService.listarTodos());
        return "portal/buscar";
    }

    @GetMapping("/portal/pago")
    public String portalPago(HttpSession session, Model model) {
        Long clienteId = (Long) session.getAttribute("clienteId");
        if (clienteId != null) {
            clienteService.buscarPorId(clienteId).ifPresent(c -> model.addAttribute("cliente", c));
        }
        return "portal/pago";
    }
}
