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

    @GetMapping
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "funcionarios";
    }

    @GetMapping("/novo")
    public String cadastrarFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario()); // Para criar um novo objeto
        return "funcionario_form";
    }

    @PostMapping("/novo")
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "redirect:/dashboard/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        model.addAttribute("funcionario", funcionario);
        return "funcionario_form";
    }

    @PostMapping("/editar")
    public String atualizarFuncionario(@ModelAttribute Funcionario funcionario) {
        funcionarioRepository.save(funcionario); // Atualiza diretamente
        return "redirect:/dashboard/funcionarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/dashboard/funcionarios";
    }
}
