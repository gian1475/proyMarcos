package com.example.demo.service;

import com.example.demo.model.Cliente;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class ClienteService extends CrudEnMemoria<Cliente> {

    @Override
    protected void copiarDatos(Cliente origen, Cliente destino) {
        destino.setTipoDocumento(origen.getTipoDocumento());
        destino.setNumeroDocumento(origen.getNumeroDocumento());
        destino.setNombres(origen.getNombres());
        destino.setApellidos(origen.getApellidos());
        destino.setTelefono(origen.getTelefono());
        destino.setCorreo(origen.getCorreo());
    }

    @Override
    protected boolean coincide(Cliente c, String q) {
        return c.getNombreCompleto().toLowerCase().contains(q)
                || c.getCorreo().toLowerCase().contains(q)
                || c.getNumeroDocumento().contains(q);
    }

    /** Registra al cliente guardando la contraseña cifrada (nunca en texto plano). */
    public Cliente registrar(Cliente cliente) {
        cliente.setCorreo(cliente.getCorreo().trim().toLowerCase());
        cliente.setContrasena(cifrar(cliente.getContrasena()));
        return agregar(cliente);
    }

    public boolean existeCorreo(String correo) {
        return contar(c -> c.getCorreo().equalsIgnoreCase(correo.trim())) > 0;
    }

    public boolean existeDocumento(String numeroDocumento) {
        return contar(c -> c.getNumeroDocumento().equals(numeroDocumento.trim())) > 0;
    }

    // TODO: reemplazar por PasswordEncoder (BCrypt) al integrar Spring Security
    private static String cifrar(String contrasena) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(contrasena.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
