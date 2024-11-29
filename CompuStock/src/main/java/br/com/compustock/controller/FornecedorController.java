package br.com.compustock.controller;

import br.com.compustock.model.Fornecedor;
import br.com.compustock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping("/fornecedores")
    public String listarFornecedores(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "fornecedores"; // Nome do template HTML
    }

    @GetMapping("/fornecedores/novo")
    public String cadastrarFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor_form"; // Nome do template HTML para o formulário
    }

    @PostMapping("/fornecedores/novo")
    public String salvarFornecedor(@ModelAttribute Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedores"; // Redireciona para a lista de fornecedores
    }

    @GetMapping("/fornecedores/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor_form"; // Nome do template HTML para o formulário
    }

    @PostMapping("/fornecedores/editar/{id}")
    public String atualizarFornecedor(@PathVariable Long id, @ModelAttribute Fornecedor fornecedor) {
        fornecedor.setId(id); // Define o ID do fornecedor
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedores"; // Redireciona para a lista de fornecedores
    }

    @GetMapping("/fornecedores/deletar/{id}")
    public String deletarFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return "redirect:/fornecedores"; // Redireciona para a lista de fornecedores
    }
}