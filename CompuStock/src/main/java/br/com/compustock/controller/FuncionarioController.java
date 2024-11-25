package br.com.compustock.controller;

import br.com.compustock.model.Funcionario;
import br.com.compustock.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Página de listagem de funcionários
    @GetMapping
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "funcionarios"; // Página de listagem de funcionários
    }

    // Página de cadastro de funcionário
    @GetMapping("/novo")
    public String cadastrarFuncionario() {
        return "funcionario_form"; // Página de cadastro de funcionário
    }

    // Processa o cadastro do funcionário
    @PostMapping
    public String salvarFuncionario(@RequestParam String nome, @RequestParam String email, 
                                    @RequestParam String telefone, @RequestParam String senha) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setTelefone(telefone);
        funcionario.setSenha(senha);
        
        funcionarioRepository.save(funcionario);
        return "redirect:/dashboard/funcionarios";
    }

    // Página de edição de funcionário
    @GetMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow();
        model.addAttribute("funcionario", funcionario);
        return "funcionario_form"; // Página de edição de funcionário
    }

    // Atualiza as informações do funcionário
    @PostMapping("/editar/{id}")
    public String atualizarFuncionario(@PathVariable Long id, @RequestParam String nome, 
                                       @RequestParam String email, @RequestParam String telefone, 
                                       @RequestParam String senha) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow();
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setTelefone(telefone);
        funcionario.setSenha(senha);

        funcionarioRepository.save(funcionario);
        return "redirect:/dashboard/funcionarios";
    }

    // Deleta um funcionário
    @GetMapping("/deletar/{id}")
    public String deletarFuncionario(@PathVariable Long id) {
        try {
            funcionarioRepository.deleteById(id);
        } catch (Exception e) {
            return "redirect:/dashboard/funcionarios?error=true";
        }
        return "redirect:/dashboard/funcionarios";
    }
}
