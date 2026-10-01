package com.example.demo.service;

import com.example.demo.model.Terminal;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService extends CrudEnMemoria<Terminal> {

    @PostConstruct
    public void init() {
        agregar(new Terminal(null, "Terminal Plaza Lima Norte", "Lima", "Av. Túpac Amaru 3900, Independencia", true));
        agregar(new Terminal(null, "Terminal Terrestre Piura", "Piura", "Av. Sánchez Cerro 1234", true));
        agregar(new Terminal(null, "Terminal Trujillo", "Trujillo", "Av. América Sur 2850", true));
        agregar(new Terminal(null, "Terminal Chiclayo", "Chiclayo", "Av. Bolognesi 714", true));
        agregar(new Terminal(null, "Terminal Cajamarca", "Cajamarca", "Av. Atahualpa 450", true));
        agregar(new Terminal(null, "Terminal Arequipa Central", "Arequipa", "Av. Andrés Avelino Cáceres s/n", true));
        agregar(new Terminal(null, "Terminal Cusco", "Cusco", "Av. Velasco Astete s/n, Wanchaq", true));
        agregar(new Terminal(null, "Terminal Huancayo", "Huancayo", "Jr. Puno 220", true));
    }

    @Override
    protected void copiarDatos(Terminal origen, Terminal destino) {
        destino.setNombreTerminal(origen.getNombreTerminal());
        destino.setCiudad(origen.getCiudad());
        destino.setDireccion(origen.getDireccion());
        destino.setEstado(origen.isEstado());
    }

    @Override
    protected boolean coincide(Terminal t, String q) {
        return t.getNombreTerminal().toLowerCase().contains(q)
                || t.getCiudad().toLowerCase().contains(q)
                || t.getDireccion().toLowerCase().contains(q);
    }

    public List<Terminal> buscarPorCiudad(String ciudad) {
        return buscar(ciudad);
    }
}
