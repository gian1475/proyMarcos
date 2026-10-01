package com.example.demo.service;

import com.example.demo.model.EstadoViaje;
import com.example.demo.model.Viaje;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ViajeService extends CrudEnMemoria<Viaje> {

    private final RutaService rutaService;
    private final BusService busService;
    private final ChoferService choferService;

    public ViajeService(RutaService rutaService, BusService busService, ChoferService choferService) {
        this.rutaService = rutaService;
        this.busService = busService;
        this.choferService = choferService;
    }

    @PostConstruct
    public void init() {
        agregar(viaje(1L, 2L, 1L, 26, 11, 0, "80.00", EstadoViaje.PROGRAMADO));
        agregar(viaje(2L, 3L, 2L, 26, 16, 0, "80.00", EstadoViaje.EN_CURSO));
        agregar(viaje(3L, 1L, 3L, 26, 8, 30, "70.00", EstadoViaje.EN_CURSO));
        agregar(viaje(4L, 4L, 4L, 25, 15, 0, "70.00", EstadoViaje.COMPLETADO));
        agregar(viaje(5L, 2L, 5L, 25, 21, 0, "75.00", EstadoViaje.COMPLETADO));
        agregar(viaje(6L, 5L, 6L, 27, 10, 0, "75.00", EstadoViaje.PROGRAMADO));
        agregar(viaje(7L, 6L, 1L, 27, 7, 0, "85.00", EstadoViaje.PROGRAMADO));
        agregar(viaje(8L, 3L, 2L, 27, 15, 30, "85.00", EstadoViaje.PROGRAMADO));
    }

    /** Viaje de ejemplo en mayo de 2026. */
    private static Viaje viaje(Long ruta, Long bus, Long chofer, int dia, int hora, int minuto,
                               String precio, EstadoViaje estado) {
        return new Viaje(null, ruta, bus, chofer, LocalDate.of(2026, 5, dia), LocalTime.of(hora, minuto),
                new BigDecimal(precio), estado);
    }

    @Override
    protected void copiarDatos(Viaje origen, Viaje destino) {
        destino.setRutaId(origen.getRutaId());
        destino.setBusId(origen.getBusId());
        destino.setChoferPrincipalId(origen.getChoferPrincipalId());
        destino.setFechaSalida(origen.getFechaSalida());
        destino.setHoraSalida(origen.getHoraSalida());
        destino.setPrecioBase(origen.getPrecioBase());
        destino.setEstadoViaje(origen.getEstadoViaje());
    }

    @Override
    protected void completar(Viaje viaje) {
        rutaService.buscarPorId(viaje.getRutaId())
                .ifPresent(r -> viaje.setDescripcionRuta(r.getDescripcionRuta()));
        busService.buscarPorId(viaje.getBusId())
                .ifPresent(b -> viaje.setPlacaBus(b.getPlaca()));
        choferService.buscarPorId(viaje.getChoferPrincipalId())
                .ifPresent(c -> viaje.setNombreChofer(c.getNombreCompleto()));
    }

    /** Cambia el estado de un viaje (iniciar / finalizar). */
    public void cambiarEstado(Long id, EstadoViaje estado) {
        buscarPorId(id).ifPresent(v -> v.setEstadoViaje(estado));
    }

    public long contarPorEstado(EstadoViaje estado) {
        return contar(v -> v.getEstadoViaje() == estado);
    }

    public Map<String, Long> contarViajesPorRuta() {
        return agrupar(v -> v.getDescripcionRuta() != null ? v.getDescripcionRuta() : "Ruta " + v.getRutaId());
    }

    public Map<String, Long> contarViajesPorMes() {
        return agrupar(v -> v.getFechaSalida().getMonth().toString().substring(0, 3));
    }

    private Map<String, Long> agrupar(Function<Viaje, String> clave) {
        return listarTodos().stream()
                .collect(Collectors.groupingBy(clave, LinkedHashMap::new, Collectors.counting()));
    }

    @Override
    protected boolean coincide(Viaje v, String q) {
        return (v.getDescripcionRuta() != null && v.getDescripcionRuta().toLowerCase().contains(q))
                || (v.getPlacaBus() != null && v.getPlacaBus().toLowerCase().contains(q))
                || v.getEstadoViaje().getDisplayName().toLowerCase().contains(q);
    }

    public List<Viaje> buscarPorEstado(EstadoViaje estado) {
        return listarTodos().stream().filter(v -> v.getEstadoViaje() == estado).toList();
    }
}
