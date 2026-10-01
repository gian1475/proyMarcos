package com.example.demo.model;

public class Terminal implements Identificable {

    private Long id;
    private String nombreTerminal;
    private String ciudad;
    private String direccion;
    private boolean estado;

    public Terminal() {
        this.estado = true;
    }

    public Terminal(Long id, String nombreTerminal, String ciudad, String direccion, boolean estado) {
        this.id = id;
        this.nombreTerminal = nombreTerminal;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTerminal() {
        return nombreTerminal;
    }

    public void setNombreTerminal(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
