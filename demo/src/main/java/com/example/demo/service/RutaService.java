package com.example.demo.service;

import com.example.demo.model.Ruta;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class RutaService extends CrudEnMemoria<Ruta> {

    private final TerminalService terminalService;

    public RutaService(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @PostConstruct
    public void init() {
        agregar(new Ruta(null, 1L, 2L, "14:00", true));  // LIMA - PIURA
        agregar(new Ruta(null, 2L, 1L, "14:00", true));  // PIURA - LIMA
        agregar(new Ruta(null, 1L, 3L, "08:30", true));  // LIMA - TRUJILLO
        agregar(new Ruta(null, 3L, 1L, "08:30", true));  // TRUJILLO - LIMA
        agregar(new Ruta(null, 1L, 4L, "12:00", true));  // LIMA - CHICLAYO
        agregar(new Ruta(null, 4L, 1L, "12:00", true));  // CHICLAYO - LIMA
        agregar(new Ruta(null, 1L, 5L, "15:30", true));  // LIMA - CAJAMARCA
        agregar(new Ruta(null, 5L, 1L, "15:30", true));  // CAJAMARCA - LIMA
    }

    @Override
    protected void copiarDatos(Ruta origen, Ruta destino) {
        destino.setTerminalOrigenId(origen.getTerminalOrigenId());
        destino.setTerminalDestinoId(origen.getTerminalDestinoId());
        destino.setDuracionEstimada(origen.getDuracionEstimada());
        destino.setActiva(origen.isActiva());
    }

    @Override
    protected void completar(Ruta ruta) {
        terminalService.buscarPorId(ruta.getTerminalOrigenId())
                .ifPresent(t -> ruta.setNombreOrigen(t.getCiudad()));
        terminalService.buscarPorId(ruta.getTerminalDestinoId())
                .ifPresent(t -> ruta.setNombreDestino(t.getCiudad()));
    }

    @Override
    protected boolean coincide(Ruta r, String q) {
        return r.getDescripcionRuta().toLowerCase().contains(q);
    }
}
