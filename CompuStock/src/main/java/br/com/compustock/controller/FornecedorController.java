package br.com.compustock.controller;

import br.com.compustock.model.Fornecedor;
import br.com.compustock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public String listarFornecedores(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "fornecedores";
    }

    @GetMapping("/novo")
    public String cadastrarFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor_form";
    }

    @PostMapping("/novo")
    public String salvarFornecedor(@ModelAttribute Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/dashboard/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor_form";
    }

    @PostMapping("/editar")
    public String atualizarFornecedor(@ModelAttribute Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/dashboard/fornecedores";
    }

    @GetMapping("/deletar/{id}")
    public String deletarFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return "redirect:/dashboard/fornecedores";
    }
}
