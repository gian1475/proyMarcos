package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Viaje implements Identificable {

    private Long id;
    private Long rutaId;
    private Long busId;
    private Long choferPrincipalId;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalDate fechaLlegadaEstimada;
    private LocalTime horaLlegadaEstimada;
    private BigDecimal precioBase;
    private EstadoViaje estadoViaje;

    // Campos auxiliares para mostrar en las vistas
    private String descripcionRuta;
    private String placaBus;
    private String nombreChofer;

    public Viaje() {
        this.estadoViaje = EstadoViaje.PROGRAMADO;
    }

    public Viaje(Long id, Long rutaId, Long busId, Long choferPrincipalId,
                 LocalDate fechaSalida, LocalTime horaSalida,
                 BigDecimal precioBase, EstadoViaje estadoViaje) {
        this.id = id;
        this.rutaId = rutaId;
        this.busId = busId;
        this.choferPrincipalId = choferPrincipalId;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.precioBase = precioBase;
        this.estadoViaje = estadoViaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRutaId() {
        return rutaId;
    }

    public void setRutaId(Long rutaId) {
        this.rutaId = rutaId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getChoferPrincipalId() {
        return choferPrincipalId;
    }

    public void setChoferPrincipalId(Long choferPrincipalId) {
        this.choferPrincipalId = choferPrincipalId;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDate getFechaLlegadaEstimada() {
        return fechaLlegadaEstimada;
    }

    public void setFechaLlegadaEstimada(LocalDate fechaLlegadaEstimada) {
        this.fechaLlegadaEstimada = fechaLlegadaEstimada;
    }

    public LocalTime getHoraLlegadaEstimada() {
        return horaLlegadaEstimada;
    }

    public void setHoraLlegadaEstimada(LocalTime horaLlegadaEstimada) {
        this.horaLlegadaEstimada = horaLlegadaEstimada;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public EstadoViaje getEstadoViaje() {
        return estadoViaje;
    }

    public void setEstadoViaje(EstadoViaje estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

    public String getDescripcionRuta() {
        return descripcionRuta;
    }

    public void setDescripcionRuta(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }

    public String getPlacaBus() {
        return placaBus;
    }

    public void setPlacaBus(String placaBus) {
        this.placaBus = placaBus;
    }

    public String getNombreChofer() {
        return nombreChofer;
    }

    public void setNombreChofer(String nombreChofer) {
        this.nombreChofer = nombreChofer;
    }
}
