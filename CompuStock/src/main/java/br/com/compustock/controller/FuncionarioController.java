package br.com.compustock.controller;

import br.com.compustock.model.Funcionario;
import br.com.compustock.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    @Transactional
    public String listarFuncionarios(Model model) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios";
    }

    @GetMapping("/novo")
    public String novoFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionario_form";
    }

    @PostMapping
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "redirect:/dashboard/funcionarios"; // Redireciona de volta para a lista de funcionários
    }

    @GetMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow();
        model.addAttribute("funcionario", funcionario);
        return "funcionario_form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarFuncionario(@PathVariable Long id, @ModelAttribute Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id).orElseThrow();
        funcionarioExistente.setNome(funcionario.getNome());
        funcionarioExistente.setEmail(funcionario.getEmail());
        funcionarioExistente.setTelefone(funcionario.getTelefone());
        funcionarioRepository.save(funcionarioExistente);
        return "redirect:/dashboard/funcionarios"; // Redireciona de volta para a lista de funcionários
    }

    @GetMapping("/deletar/{id}")
    public String deletarFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/dashboard/funcionarios"; // Redireciona de volta para a lista de funcionários
    }
}
