package br.com.compustock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Página inicial do dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";  // Página do dashboard
    }
}
