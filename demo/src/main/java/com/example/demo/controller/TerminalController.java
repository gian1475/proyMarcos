package com.example.demo.controller;

import com.example.demo.model.Terminal;
import com.example.demo.service.TerminalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/terminales")
public class TerminalController extends CrudController<Terminal> {

    public TerminalController(TerminalService terminalService) {
        super(terminalService, "terminales", "terminal", "Terminal", false);
    }

    @Override
    protected Terminal nuevo() {
        return new Terminal();
    }
}
