package br.com.compustock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Página do dashboard - Acessada após o login
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";  // Retorna a página do dashboard
    }
}
