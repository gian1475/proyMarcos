package com.example.demo.controller;

import com.example.demo.config.AutenticacionInterceptor;
import com.example.demo.model.Asiento;
import com.example.demo.model.Boleto;
import com.example.demo.model.EstadoAbordaje;
import com.example.demo.model.EstadoViaje;
import com.example.demo.model.ResumenAbordaje;
import com.example.demo.model.UsuarioSesion;
import com.example.demo.model.Viaje;
import com.example.demo.service.AbordajeService;
import com.example.demo.service.BusService;
import com.example.demo.service.ChoferService;
import com.example.demo.service.ViajeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Vista del Supervisor de Terminal: control de embarque y plano de asientos. */
@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    /** Viaje que se muestra por defecto (el que tiene boletos de ejemplo). */
    private static final Long VIAJE_POR_DEFECTO = 1L;
    private static final int ASIENTOS_POR_BANDA = 16;

    private final AbordajeService abordajeService;
    private final ViajeService viajeService;
    private final BusService busService;
    private final ChoferService choferService;

    public SupervisorController(AbordajeService abordajeService, ViajeService viajeService,
                                BusService busService, ChoferService choferService) {
        this.abordajeService = abordajeService;
        this.viajeService = viajeService;
        this.busService = busService;
        this.choferService = choferService;
    }

    // ---------------------------------------------------------------- EMBARQUE

    @GetMapping("/embarque")
    public String embarque(@RequestParam(required = false) String q,
                           @RequestParam(defaultValue = "placa") String modo,
                           @RequestParam(required = false) Long viajeId, Model model) {
        List<Viaje> resultados = buscarViajes(q, modo);
        Long id = viajeId != null ? viajeId
                : (!resultados.isEmpty() && q != null && !q.isBlank() ? resultados.get(0).getId() : VIAJE_POR_DEFECTO);

        model.addAttribute("q", q);
        model.addAttribute("modo", modo);
        model.addAttribute("resultados", q == null || q.isBlank() ? List.of() : resultados);
        model.addAttribute("sinResultados", q != null && !q.isBlank() && resultados.isEmpty());
        cargarViaje(model, id);
        return "supervisor/embarque";
    }

    @PostMapping("/embarque/boleto/{id}/{accion}")
    public String marcarDesdeManifiesto(@PathVariable Long id, @PathVariable String accion,
                                        HttpSession session) {
        EstadoAbordaje estado = "abordo".equals(accion) ? EstadoAbordaje.ABORDO : EstadoAbordaje.AUSENTE;
        Optional<Boleto> boleto = abordajeService.marcar(id, estado, nombreSupervisor(session));
        return "redirect:/supervisor/embarque?viajeId=" + boleto.map(Boleto::getViajeId).orElse(VIAJE_POR_DEFECTO);
    }

    @PostMapping("/embarque/viaje/{id}/{accion}")
    public String cambiarViaje(@PathVariable Long id, @PathVariable String accion) {
        viajeService.cambiarEstado(id, "iniciar".equals(accion) ? EstadoViaje.EN_CURSO : EstadoViaje.COMPLETADO);
        return "redirect:/supervisor/embarque?viajeId=" + id;
    }

    // ------------------------------------------------------------------ PLANO

    @GetMapping("/plano-asientos")
    public String plano(@RequestParam(required = false) Long viajeId, Model model) {
        Long id = viajeId != null ? viajeId : VIAJE_POR_DEFECTO;
        cargarViaje(model, id);

        List<Asiento> asientos = abordajeService.asientosDeViaje(id);
        List<List<List<Asiento>>> bandas = new ArrayList<>();
        for (int i = 0; i < asientos.size(); i += ASIENTOS_POR_BANDA) {
            List<List<Asiento>> columnas = new ArrayList<>();
            List<Asiento> banda = asientos.subList(i, Math.min(i + ASIENTOS_POR_BANDA, asientos.size()));
            for (int j = 0; j < banda.size(); j += 2) {
                columnas.add(banda.subList(j, Math.min(j + 2, banda.size())));
            }
            bandas.add(columnas);
        }
        model.addAttribute("bandas", bandas);
        return "supervisor/plano-asientos";
    }

    /** El popover del plano usa este endpoint (JSON) para no salir de la pantalla. */
    @PostMapping("/api/boletos/{id}/estado")
    @ResponseBody
    public Map<String, Object> cambiarEstado(@PathVariable Long id, @RequestParam EstadoAbordaje estado,
                                             HttpSession session) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        Optional<Boleto> boleto = abordajeService.marcar(id, estado, nombreSupervisor(session));
        if (boleto.isEmpty()) {
            respuesta.put("ok", false);
            return respuesta;
        }
        ResumenAbordaje r = abordajeService.resumen(boleto.get().getViajeId());
        respuesta.put("ok", true);
        respuesta.put("estado", estado.getCss());
        respuesta.put("estadoTexto", estado.getDisplayName());
        respuesta.put("hora", boleto.get().getHoraAbordajeTexto());
        respuesta.put("abordo", r.getAbordo());
        respuesta.put("ausente", r.getAusente());
        respuesta.put("pendientes", r.getPendientes());
        respuesta.put("libres", r.getLibres());
        return respuesta;
    }

    // ---------------------------------------------------------------- AUXILIARES

    private List<Viaje> buscarViajes(String q, String modo) {
        if (q == null || q.isBlank()) {
            return List.of();
        }
        String texto = q.trim().toLowerCase();
        return viajeService.listarTodos().stream()
                .filter(v -> "horario".equals(modo)
                        ? v.getHoraSalida().toString().contains(texto)
                        : v.getPlacaBus() != null && v.getPlacaBus().toLowerCase().contains(texto))
                .toList();
    }

    /** Datos comunes de la ficha del bus, ocupación y manifiesto. */
    private void cargarViaje(Model model, Long viajeId) {
        Viaje viaje = viajeService.buscarPorId(viajeId).orElseGet(() -> viajeService.buscarPorId(VIAJE_POR_DEFECTO).get());
        model.addAttribute("viaje", viaje);
        busService.buscarPorId(viaje.getBusId()).ifPresent(b -> model.addAttribute("bus", b));
        choferService.buscarPorId(viaje.getChoferPrincipalId()).ifPresent(c -> model.addAttribute("chofer", c));
        model.addAttribute("resumen", abordajeService.resumen(viaje.getId()));
        model.addAttribute("boletos", abordajeService.listarPorViaje(viaje.getId()));
        model.addAttribute("ahora", LocalDateTime.now());
    }

    private String nombreSupervisor(HttpSession session) {
        UsuarioSesion u = (UsuarioSesion) session.getAttribute(AutenticacionInterceptor.USUARIO);
        return u != null ? u.getNombre() : "Supervisor";
    }
}
