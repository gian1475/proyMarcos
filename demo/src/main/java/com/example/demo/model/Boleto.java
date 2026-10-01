package com.example.demo.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Pasaje vendido para un asiento de un viaje (equivale a detalle_venta + pasajero). */
public class Boleto implements Identificable {

    private Long id;
    private Long viajeId;
    private String numeroAsiento;
    private String nombrePasajero;
    private String documento;
    private EstadoAbordaje estado = EstadoAbordaje.PENDIENTE;
    private LocalTime horaAbordaje;
    private String supervisor;

    public Boleto() {
    }

    public Boleto(Long viajeId, String numeroAsiento, String nombrePasajero, String documento) {
        this.viajeId = viajeId;
        this.numeroAsiento = numeroAsiento;
        this.nombrePasajero = nombrePasajero;
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public void setViajeId(Long viajeId) {
        this.viajeId = viajeId;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public EstadoAbordaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoAbordaje estado) {
        this.estado = estado;
    }

    public LocalTime getHoraAbordaje() {
        return horaAbordaje;
    }

    public void setHoraAbordaje(LocalTime horaAbordaje) {
        this.horaAbordaje = horaAbordaje;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getHoraAbordajeTexto() {
        return horaAbordaje == null ? "-" : horaAbordaje.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
