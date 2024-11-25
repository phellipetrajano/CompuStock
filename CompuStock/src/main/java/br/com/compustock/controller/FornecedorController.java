package br.com.compustock.controller;

import br.com.compustock.model.Fornecedor;
import br.com.compustock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    @Transactional
    public String listarFornecedores(Model model) {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "fornecedores";
    }

    @GetMapping("/novo")
    public String novoFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor_form";
    }

    @PostMapping
    public String salvarFornecedor(@ModelAttribute Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/dashboard/fornecedores"; // Redireciona para a lista de fornecedores
    }

    @GetMapping("/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor_form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarFornecedor(@PathVariable Long id, @ModelAttribute Fornecedor fornecedor) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id).orElseThrow();
        fornecedorExistente.setNome(fornecedor.getNome());
        fornecedorExistente.setEmail(fornecedor.getEmail());
        fornecedorExistente.setTelefone(fornecedor.getTelefone());
        fornecedorRepository.save(fornecedorExistente);
        return "redirect:/dashboard/fornecedores"; // Redireciona para a lista de fornecedores
    }

    @GetMapping("/deletar/{id}")
    public String deletarFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return "redirect:/dashboard/fornecedores"; // Redireciona para a lista de fornecedores
    }
}
