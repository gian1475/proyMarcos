package com.example.demo.controller;

import com.example.demo.model.Chofer;
import com.example.demo.service.ChoferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/choferes")
public class ChoferController extends CrudController<Chofer> {

    public ChoferController(ChoferService choferService) {
        super(choferService, "choferes", "chofer", "Usuario", false);
    }

    @Override
    protected Chofer nuevo() {
        return new Chofer();
    }
}
