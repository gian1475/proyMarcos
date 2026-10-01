package com.example.demo.service;

import com.example.demo.model.Identificable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

/**
 * Operaciones CRUD comunes sobre una lista en memoria.
 * Cada servicio solo define cómo copiar los datos editables y, si lo necesita,
 * cómo completar los campos auxiliares que se muestran en las vistas.
 */
public abstract class CrudEnMemoria<T extends Identificable> {

    private final List<T> items = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    /** Copia los campos editables de {@code origen} a {@code destino}. */
    protected abstract void copiarDatos(T origen, T destino);

    /** Rellena campos auxiliares (nombres, placas, etc.). Por defecto no hace nada. */
    protected void completar(T item) {
    }

    public List<T> listarTodos() {
        items.forEach(this::completar);
        return new ArrayList<>(items);
    }

    public Optional<T> buscarPorId(Long id) {
        Optional<T> item = items.stream().filter(i -> i.getId().equals(id)).findFirst();
        item.ifPresent(this::completar);
        return item;
    }

    /** Indica si el elemento coincide con el texto buscado (ya en minúsculas). */
    protected abstract boolean coincide(T item, String texto);

    /** Búsqueda por texto libre sobre los campos principales de cada entidad. */
    public List<T> buscar(String texto) {
        if (texto == null || texto.isBlank()) {
            return listarTodos();
        }
        String q = texto.trim().toLowerCase();
        return listarTodos().stream().filter(i -> coincide(i, q)).toList();
    }

    public T agregar(T item) {
        item.setId(secuencia.incrementAndGet());
        completar(item);
        items.add(item);
        return item;
    }

    public T actualizar(Long id, T datos) {
        return buscarPorId(id).map(existente -> {
            copiarDatos(datos, existente);
            completar(existente);
            return existente;
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        return items.removeIf(i -> i.getId().equals(id));
    }

    protected long contar(Predicate<T> filtro) {
        return items.stream().filter(filtro).count();
    }
}
