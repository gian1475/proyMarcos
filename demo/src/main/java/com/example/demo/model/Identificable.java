package com.example.demo.model;

/** Entidad con identificador numérico, usada por los servicios y controladores CRUD genéricos. */
public interface Identificable {

    Long getId();

    void setId(Long id);
}
