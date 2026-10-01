package com.example.demo.model;

public enum Rol {
    ADMIN("Admin", "/admin/dashboard"),
    SUPERVISOR("Supervisor", "/supervisor/embarque");

    private final String displayName;
    private final String inicio;

    Rol(String displayName, String inicio) {
        this.displayName = displayName;
        this.inicio = inicio;
    }

    public String getDisplayName() {
        return displayName;
    }

    /** Página a la que va el usuario tras iniciar sesión. */
    public String getInicio() {
        return inicio;
    }
}
