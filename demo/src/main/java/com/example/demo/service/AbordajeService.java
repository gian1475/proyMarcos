package com.example.demo.service;

import com.example.demo.model.Asiento;
import com.example.demo.model.Boleto;
import com.example.demo.model.Bus;
import com.example.demo.model.EstadoAbordaje;
import com.example.demo.model.ResumenAbordaje;
import com.example.demo.model.Viaje;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Boletos vendidos y control de abordaje (equivale a detalle_venta en la futura BD). */
@Service
public class AbordajeService extends CrudEnMemoria<Boleto> {

    private final ViajeService viajeService;
    private final BusService busService;

    public AbordajeService(ViajeService viajeService, BusService busService) {
        this.viajeService = viajeService;
        this.busService = busService;
    }

    /** Pasajeros de ejemplo del viaje 1 (CHK-202, 32 asientos): 26 vendidos = 21 abordó, 2 ausente, 3 pendiente. */
    @PostConstruct
    public void init() {
        String[][] pasajeros = {
                {"01A", "Elena Quispe Huamán", "74112233"}, {"01B", "Pedro Salazar Díaz", "70112244"},
                {"02A", "Carmen Rojas Vera", "72334455"}, {"03A", "Jorge Paredes Ríos", "71445566"},
                {"03B", "Lucía Ccori Mamani", "75556677"}, {"05A", "María Fernanda López", "74589621"},
                {"05B", "José Antonio García", "70258410"}, {"06A", "Luis Alberto Silva", "72541236"},
                {"06B", "Ana Lucía Ramírez", "76854123"}, {"07A", "Carlos Enrique Torres", "72584211"},
                {"07B", "Rosa Elena Chunga", "70784512"}, {"08A", "Miguel Ángel Castro", "73321456"},
                {"08B", "Diana Carolina Ruiz", "75896321"}, {"09A", "Víctor Hugo Neyra", "71236547"},
                {"09B", "Patricia Alva Cruz", "70369852"}, {"10A", "Walter Benites Gil", "72145896"},
                {"11A", "Sandra Mego Tello", "73698521"}, {"11B", "Raúl Távara Peña", "74125896"},
                {"12A", "Gladys Ñique Yarlequé", "70987456"}, {"13A", "Hugo Zapata Lama", "71478523"},
                {"14A", "Milagros Sánchez Orbe", "72369874"}, {"14B", "Óscar Farfán Jara", "75123698"},
                {"15A", "Teresa Mendoza Ayala", "70147852"}, {"16A", "Iván Ordinola Cruz", "73258741"},
                {"16B", "Julia Pazos Ramos", "74852963"}, {"15B", "Felipe Calle Reyes", "71963258"}};
        List<String> ausentes = List.of("08A", "10A");
        List<String> pendientes = List.of("06B", "07A", "07B");

        LocalTime hora = LocalTime.of(10, 25);
        for (String[] p : pasajeros) {
            Boleto b = new Boleto(1L, p[0], p[1], p[2]);
            if (ausentes.contains(p[0])) {
                b.setEstado(EstadoAbordaje.AUSENTE);
            } else if (!pendientes.contains(p[0])) {
                b.setEstado(EstadoAbordaje.ABORDO);
                b.setHoraAbordaje(hora);
                b.setSupervisor("Juan Pérez");
                hora = hora.plusMinutes(2);
            }
            agregar(b);
        }
    }

    @Override
    protected void copiarDatos(Boleto origen, Boleto destino) {
        destino.setNombrePasajero(origen.getNombrePasajero());
        destino.setDocumento(origen.getDocumento());
        destino.setNumeroAsiento(origen.getNumeroAsiento());
    }

    @Override
    protected boolean coincide(Boleto b, String q) {
        return b.getNombrePasajero().toLowerCase().contains(q)
                || b.getDocumento().contains(q)
                || b.getNumeroAsiento().toLowerCase().contains(q);
    }

    public List<Boleto> listarPorViaje(Long viajeId) {
        return listarTodos().stream()
                .filter(b -> b.getViajeId().equals(viajeId))
                .sorted(Comparator.comparing(Boleto::getNumeroAsiento))
                .toList();
    }

    public ResumenAbordaje resumen(Long viajeId) {
        List<Boleto> boletos = listarPorViaje(viajeId);
        int abordo = (int) boletos.stream().filter(b -> b.getEstado() == EstadoAbordaje.ABORDO).count();
        int ausente = (int) boletos.stream().filter(b -> b.getEstado() == EstadoAbordaje.AUSENTE).count();
        return new ResumenAbordaje(capacidad(viajeId), boletos.size(), abordo, ausente,
                boletos.size() - abordo - ausente);
    }

    /** Marca el boleto como ABORDO / AUSENTE y registra la hora y el supervisor. */
    public Optional<Boleto> marcar(Long boletoId, EstadoAbordaje estado, String supervisor) {
        return buscarPorId(boletoId).map(b -> {
            b.setEstado(estado);
            b.setHoraAbordaje(estado == EstadoAbordaje.ABORDO ? LocalTime.now().withSecond(0).withNano(0) : null);
            b.setSupervisor(supervisor);
            return b;
        });
    }

    /** Asientos del bus (pares A ventana / B pasillo por fila) cruzados con los boletos vendidos. */
    public List<Asiento> asientosDeViaje(Long viajeId) {
        List<Boleto> boletos = listarPorViaje(viajeId);
        int total = capacidad(viajeId);
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            String numero = String.format("%02d%s", i / 2 + 1, i % 2 == 0 ? "A" : "B");
            Boleto boleto = boletos.stream().filter(b -> b.getNumeroAsiento().equals(numero)).findFirst().orElse(null);
            asientos.add(new Asiento(numero, i % 2 == 0 ? "Ventana" : "Pasillo", 1, boleto));
        }
        return asientos;
    }

    public int capacidad(Long viajeId) {
        return viajeService.buscarPorId(viajeId)
                .flatMap(v -> busService.buscarPorId(v.getBusId()))
                .map(Bus::getCapacidadAsientos)
                .orElse(0);
    }

    public Optional<Viaje> viaje(Long viajeId) {
        return viajeService.buscarPorId(viajeId);
    }
}
