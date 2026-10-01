package com.example.demo.model;

public enum TipoServicio {
    ECONOMICO("Económico"),
    CONFORT("Confort"),
    IMPERIAL("Imperial");

    private final String displayName;

    TipoServicio(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
