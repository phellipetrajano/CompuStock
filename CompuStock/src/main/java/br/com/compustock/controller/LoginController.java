package br.com.compustock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    // Página de login - Método GET para carregar o formulário de login
    @GetMapping("/login")
    public String loginForm() {
        return "login";  // Retorna a página de login
    }

    // Processamento do login - Não usamos POST, mas redirecionamos diretamente para o dashboard
    @PostMapping("/login")
    public String processLogin() {
        // Lógica de autenticação aqui (não implementada neste exemplo)
        // Se o login for bem-sucedido, redireciona para o dashboard
        return "redirect:/dashboard";  // Redireciona para /dashboard após o login
    }
}
