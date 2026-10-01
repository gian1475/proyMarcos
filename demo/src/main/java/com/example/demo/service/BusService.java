package com.example.demo.service;

import com.example.demo.model.Bus;
import com.example.demo.model.EstadoBus;
import com.example.demo.model.TipoServicio;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService extends CrudEnMemoria<Bus> {

    @PostConstruct
    public void init() {
        agregar(new Bus(null, "CHK-201", "Marcopolo G7 1200", TipoServicio.ECONOMICO, 20, EstadoBus.ACTIVO));
        agregar(new Bus(null, "CHK-202", "Yutong ZK61218H", TipoServicio.CONFORT, 32, EstadoBus.ACTIVO));
        agregar(new Bus(null, "CHK-203", "Marcopolo Paradiso 1800", TipoServicio.IMPERIAL, 40, EstadoBus.ACTIVO));
        agregar(new Bus(null, "CHK-204", "Scania K360", TipoServicio.CONFORT, 32, EstadoBus.ACTIVO));
        agregar(new Bus(null, "CHK-205", "Marcopolo G7 1200", TipoServicio.ECONOMICO, 20, EstadoBus.MANTENIMIENTO));
        agregar(new Bus(null, "CHK-206", "Yutong ZK61218H", TipoServicio.CONFORT, 32, EstadoBus.ACTIVO));
        agregar(new Bus(null, "CHK-207", "Marcopolo Paradiso 1200", TipoServicio.ECONOMICO, 20, EstadoBus.MANTENIMIENTO));
        agregar(new Bus(null, "CHK-208", "Scania K360", TipoServicio.IMPERIAL, 40, EstadoBus.ACTIVO));
    }

    @Override
    protected void copiarDatos(Bus origen, Bus destino) {
        destino.setPlaca(origen.getPlaca());
        destino.setModelo(origen.getModelo());
        destino.setTipoServicio(origen.getTipoServicio());
        destino.setCapacidadAsientos(origen.getCapacidadAsientos());
        destino.setEstadoBus(origen.getEstadoBus());
    }

    public long contarPorServicio(TipoServicio tipo) {
        return contar(b -> b.getTipoServicio() == tipo);
    }

    public long contarPorEstado(EstadoBus estado) {
        return contar(b -> b.getEstadoBus() == estado);
    }

    @Override
    protected boolean coincide(Bus b, String q) {
        return b.getPlaca().toLowerCase().contains(q)
                || b.getModelo().toLowerCase().contains(q)
                || b.getTipoServicio().getDisplayName().toLowerCase().contains(q)
                || b.getEstadoBus().name().toLowerCase().contains(q);
    }

    public Optional<Bus> buscarPorPlaca(String placa) {
        return listarTodos().stream().filter(b -> b.getPlaca().equalsIgnoreCase(placa)).findFirst();
    }

    public List<Bus> buscarPorEstado(EstadoBus estado) {
        return listarTodos().stream().filter(b -> b.getEstadoBus() == estado).toList();
    }

    public List<Bus> buscarPorServicio(TipoServicio tipo) {
        return listarTodos().stream().filter(b -> b.getTipoServicio() == tipo).toList();
    }
}
