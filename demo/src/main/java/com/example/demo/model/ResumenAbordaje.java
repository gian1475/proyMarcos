package com.example.demo.model;

/** Contadores de ocupación y abordaje de un viaje. */
public class ResumenAbordaje {

    private final int totalAsientos;
    private final int vendidos;
    private final int abordo;
    private final int ausente;
    private final int pendientes;

    public ResumenAbordaje(int totalAsientos, int vendidos, int abordo, int ausente, int pendientes) {
        this.totalAsientos = totalAsientos;
        this.vendidos = vendidos;
        this.abordo = abordo;
        this.ausente = ausente;
        this.pendientes = pendientes;
    }

    public int getTotalAsientos() {
        return totalAsientos;
    }

    public int getVendidos() {
        return vendidos;
    }

    public int getAbordo() {
        return abordo;
    }

    public int getAusente() {
        return ausente;
    }

    public int getPendientes() {
        return pendientes;
    }

    public int getLibres() {
        return totalAsientos - vendidos;
    }

    public int getOcupacionPct() {
        return totalAsientos == 0 ? 0 : Math.round(vendidos * 100f / totalAsientos);
    }
}
