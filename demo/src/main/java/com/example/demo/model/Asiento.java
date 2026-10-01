package com.example.demo.model;

/** Asiento físico del bus para el plano; {@code boleto} es null si está libre. */
public class Asiento {

    private final String numero;
    private final String ubicacion;
    private final int piso;
    private final Boleto boleto;

    public Asiento(String numero, String ubicacion, int piso, Boleto boleto) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.piso = piso;
        this.boleto = boleto;
    }

    public String getNumero() {
        return numero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public int getPiso() {
        return piso;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    /** Sufijo CSS: abordo / pendiente / ausente / libre. */
    public String getCss() {
        return boleto == null ? "libre" : boleto.getEstado().getCss();
    }
}
