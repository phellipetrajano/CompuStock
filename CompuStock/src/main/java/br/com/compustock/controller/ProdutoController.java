package br.com.compustock.controller;

import br.com.compustock.model.Produto;
import br.com.compustock.repository.ProdutoRepository;
import br.com.compustock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller

public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping ("/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos";
    }

    @GetMapping("/produtos/novo")
    public String cadastrarProduto(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "produto_form";
    }

    @PostMapping("/produtos/novo")
    public String salvarProduto(@ModelAttribute Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        model.addAttribute("produto", produto);
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "produto_form";
    }

    @PostMapping("/produtos/editar")
    public String atualizarProduto(@ModelAttribute Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos";
    }
}
