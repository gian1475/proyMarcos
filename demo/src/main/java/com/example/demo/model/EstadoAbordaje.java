package com.example.demo.model;

public enum EstadoAbordaje {
    PENDIENTE("Pendiente", "pendiente"),
    ABORDO("Abordó", "abordo"),
    AUSENTE("Ausente", "ausente");

    private final String displayName;
    private final String css;

    EstadoAbordaje(String displayName, String css) {
        this.displayName = displayName;
        this.css = css;
    }

    public String getDisplayName() {
        return displayName;
    }

    /** Sufijo de clase CSS (badge-abordaje-x / asiento-x). */
    public String getCss() {
        return css;
    }
}
