package com.example.demo.model;

/** Usuario interno que inició sesión (Admin o Supervisor). */
public class UsuarioSesion {

    private final String nombre;
    private final String correo;
    private final String clave;
    private final Rol rol;
    private final String terminal;

    public UsuarioSesion(String nombre, String correo, String clave, Rol rol, String terminal) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.terminal = terminal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public Rol getRol() {
        return rol;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getIniciales() {
        String[] partes = nombre.split(" ");
        String ini = partes[0].substring(0, 1) + (partes.length > 1 ? partes[1].substring(0, 1) : "");
        return ini.toUpperCase();
    }
}
