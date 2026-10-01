package com.example.demo.controller;

import com.example.demo.model.Identificable;
import com.example.demo.service.CrudEnMemoria;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Listar / guardar / editar / eliminar comunes a los módulos del Backoffice.
 * La vista es "{modulo}/lista", la lista se expone como "{modulo}" y el formulario como "{atributo}".
 */
public abstract class CrudController<T extends Identificable> {

    private final CrudEnMemoria<T> servicio;
    private final String modulo;
    private final String atributo;
    private final String entidad;
    private final boolean femenino;

    protected CrudController(CrudEnMemoria<T> servicio, String modulo, String atributo,
                             String entidad, boolean femenino) {
        this.servicio = servicio;
        this.modulo = modulo;
        this.atributo = atributo;
        this.entidad = entidad;
        this.femenino = femenino;
    }

    /** Instancia vacía para el formulario de registro. */
    protected abstract T nuevo();

    /** Datos extra que necesita el formulario (selects, enums...). */
    protected void cargarCatalogos(Model model) {
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        return mostrar(model, nuevo(), false, q);
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        return mostrar(model, servicio.buscarPorId(id).orElseGet(this::nuevo), true, null);
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute T item, RedirectAttributes redirect) {
        boolean existente = item.getId() != null && item.getId() > 0;
        if (existente) {
            servicio.actualizar(item.getId(), item);
        } else {
            servicio.agregar(item);
        }
        return volver(redirect, existente ? "actualizad" : "registrad");
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        servicio.eliminar(id);
        return volver(redirect, "eliminad");
    }

    private String mostrar(Model model, T item, boolean editando, String q) {
        model.addAttribute(modulo, servicio.buscar(q));
        model.addAttribute("q", q);
        model.addAttribute(atributo, item);
        model.addAttribute("editando", editando);
        cargarCatalogos(model);
        return modulo + "/lista";
    }

    private String volver(RedirectAttributes redirect, String accion) {
        redirect.addFlashAttribute("msgExito", entidad + " " + accion + (femenino ? "a" : "o") + " correctamente");
        return "redirect:/admin/" + modulo;
    }
}
