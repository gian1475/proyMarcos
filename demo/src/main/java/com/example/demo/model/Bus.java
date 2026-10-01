package com.example.demo.model;

public class Bus implements Identificable {

    private Long id;
    private String placa;
    private String modelo;
    private TipoServicio tipoServicio;
    private int capacidadAsientos;
    private EstadoBus estadoBus;

    public Bus() {
        this.estadoBus = EstadoBus.ACTIVO;
    }

    public Bus(Long id, String placa, String modelo, TipoServicio tipoServicio,
               int capacidadAsientos, EstadoBus estadoBus) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.tipoServicio = tipoServicio;
        this.capacidadAsientos = capacidadAsientos;
        this.estadoBus = estadoBus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public int getCapacidadAsientos() {
        return capacidadAsientos;
    }

    public void setCapacidadAsientos(int capacidadAsientos) {
        this.capacidadAsientos = capacidadAsientos;
    }

    public EstadoBus getEstadoBus() {
        return estadoBus;
    }

    public void setEstadoBus(EstadoBus estadoBus) {
        this.estadoBus = estadoBus;
    }
}
