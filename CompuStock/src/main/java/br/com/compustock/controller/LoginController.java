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

    // Processa o login (POST)
    @PostMapping("/login")
    public String processLogin(String email, String password) {
        // Aqui você pode adicionar a lógica para verificar as credenciais do usuário
        // Por exemplo, você pode verificar se o email e a senha são válidos no banco de dados.

        if (email.equals("admin@example.com") && password.equals("admin")) {
            // Simula um login bem-sucedido
            return "redirect:/dashboard"; // Redireciona para o dashboard
        }

        // Caso o login falhe, você pode redirecionar para a página de login novamente com uma mensagem de erro
        return "redirect:/login?error"; // Redireciona de volta para o login com erro
    }
}
