package com.example.demo.model;

public class Ruta implements Identificable {

    private Long id;
    private Long terminalOrigenId;
    private Long terminalDestinoId;
    private String duracionEstimada;
    private boolean activa;

    // Campos auxiliares para mostrar nombres en las vistas
    private String nombreOrigen;
    private String nombreDestino;

    public Ruta() {
        this.activa = true;
    }

    public Ruta(Long id, Long terminalOrigenId, Long terminalDestinoId,
                String duracionEstimada, boolean activa) {
        this.id = id;
        this.terminalOrigenId = terminalOrigenId;
        this.terminalDestinoId = terminalDestinoId;
        this.duracionEstimada = duracionEstimada;
        this.activa = activa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTerminalOrigenId() {
        return terminalOrigenId;
    }

    public void setTerminalOrigenId(Long terminalOrigenId) {
        this.terminalOrigenId = terminalOrigenId;
    }

    public Long getTerminalDestinoId() {
        return terminalDestinoId;
    }

    public void setTerminalDestinoId(Long terminalDestinoId) {
        this.terminalDestinoId = terminalDestinoId;
    }

    public String getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(String duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getDescripcionRuta() {
        String origen = nombreOrigen != null ? nombreOrigen : "Terminal " + terminalOrigenId;
        String destino = nombreDestino != null ? nombreDestino : "Terminal " + terminalDestinoId;
        return origen + " → " + destino;
    }
}
