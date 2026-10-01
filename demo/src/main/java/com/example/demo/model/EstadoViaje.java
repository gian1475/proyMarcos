package com.example.demo.model;

public enum EstadoViaje {
    PROGRAMADO("Programado", "badge-estado-programado"),
    EN_CURSO("En Ruta", "badge-estado-enruta"),
    COMPLETADO("Finalizado", "badge-estado-finalizado"),
    CANCELADO("Cancelado", "badge-estado-inactivo");

    private final String displayName;
    private final String badge;

    EstadoViaje(String displayName, String badge) {
        this.displayName = displayName;
        this.badge = badge;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBadge() {
        return badge;
    }
}
