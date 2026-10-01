package com.example.demo.service;

import com.example.demo.model.Chofer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoferService extends CrudEnMemoria<Chofer> {

    @PostConstruct
    public void init() {
        agregar(new Chofer("Admin", "General", "admin@loschaskis.com", "Administrador", true));
        agregar(new Chofer("Carlos", "Mendoza", "cmendoza@loschaskis.com", "Supervisor", true));
        agregar(new Chofer("María", "Fernández", "mfernandez@loschaskis.com", "Supervisor", true));
        agregar(new Chofer("Luis", "Ramírez", "lramirez@loschaskis.com", "Supervisor", false));
        agregar(new Chofer("Sofía", "Vargas", "svargas@loschaskis.com", "Supervisor", true));
        agregar(new Chofer("Diego", "Torres", "dtorres@loschaskis.com", "Administrador", true));
    }

    @Override
    public Chofer agregar(Chofer chofer) {
        super.agregar(chofer);
        // Datos de ejemplo para los registros iniciales que no traen documento ni licencia
        if (chofer.getNumeroDocumento() == null || chofer.getNumeroDocumento().isBlank()) {
            chofer.setNumeroDocumento(String.valueOf(70000000 + chofer.getId()));
        }
        if (chofer.getNumeroLicencia() == null || chofer.getNumeroLicencia().isBlank()) {
            chofer.setNumeroLicencia("Q" + (10000000 + chofer.getId()));
        }
        return chofer;
    }

    @Override
    protected void copiarDatos(Chofer origen, Chofer destino) {
        destino.setTipoDocumento(origen.getTipoDocumento());
        destino.setNumeroDocumento(origen.getNumeroDocumento());
        destino.setNombres(origen.getNombres());
        destino.setApellidos(origen.getApellidos());
        destino.setCorreoElectronico(origen.getCorreoElectronico());
        destino.setRol(origen.getRol());
        destino.setEstado(origen.isEstado());
        if (origen.getNumeroLicencia() != null) {
            destino.setNumeroLicencia(origen.getNumeroLicencia());
        }
    }

    @Override
    protected boolean coincide(Chofer c, String q) {
        return c.getNombreCompleto().toLowerCase().contains(q)
                || c.getCorreoElectronico().toLowerCase().contains(q)
                || c.getRol().toLowerCase().contains(q);
    }

    public List<Chofer> buscarPorNombre(String nombre) {
        return buscar(nombre);
    }
}
