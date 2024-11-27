package br.com.compustock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    // Exibe o formulário de login (GET)
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // Página de login (login.html)
    }
    

}
