package br.com.compustock.controller;

import br.com.compustock.model.Funcionario;
import br.com.compustock.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/funcionarios")
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "funcionarios";  // Nome do template para listagem de funcionários
    }

    @GetMapping("/funcionarios/novo")
    public String cadastrarFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario()); // Para criar um novo objeto
        return "funcionario_form";  // Nome do template para o formulário de cadastro
    }

    @PostMapping("/funcionarios/novo")
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        funcionario.setSenha(encoder.encode(funcionario.getSenha()));  // Criptografar senha
        funcionarioRepository.save(funcionario);  // Salvar no banco
        return "redirect:/funcionarios";  // Redirecionar para a lista de funcionários
    }

    @GetMapping("/funcionarios/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario != null) {
            model.addAttribute("funcionario", funcionario);
            return "funcionario_form";  // Nome do template para edição
        }
        return "redirect:/funcionarios";  // Se não encontrar o funcionário, redireciona para a lista
    }

    @PostMapping("/funcionarios/editar/{id}")
    public String atualizarFuncionario(@PathVariable Long id, @ModelAttribute Funcionario funcionario) {
        Funcionario existingFuncionario = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        existingFuncionario.setNome(funcionario.getNome());
        existingFuncionario.setEmail(funcionario.getEmail());
        existingFuncionario.setTelefone(funcionario.getTelefone());
        existingFuncionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha())); // Criptografar senha
        funcionarioRepository.save(existingFuncionario);  // Salvar no banco
        return "redirect:/funcionarios";  // Redireciona para a lista de funcionários
    }

    @GetMapping("/funcionarios/deletar/{id}")
    public String deletarFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/funcionarios";  // Redireciona para a lista de funcionários após exclusão
    }
}
