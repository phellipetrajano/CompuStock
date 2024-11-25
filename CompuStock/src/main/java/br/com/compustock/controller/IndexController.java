package br.com.compustock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Página principal
    @GetMapping("/")
    public String index(Model model) {
        // Aqui você pode adicionar dados à página inicial se necessário
        model.addAttribute("message", "Bem-vindo ao CompuStock");
        return "index"; // Nome do template HTML (index.html)
    }
}
