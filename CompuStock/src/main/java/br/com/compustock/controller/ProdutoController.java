package br.com.compustock.controller;

import br.com.compustock.model.Produto;
import br.com.compustock.repository.ProdutoRepository;
import br.com.compustock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Página de listagem de produtos
    @GetMapping
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos"; // Página de listagem de produtos
    }

    // Página de cadastro de produto
    @GetMapping("/novo")
    public String cadastrarProduto(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "produto_form"; // Página de cadastro de produto
    }

    // Processa o cadastro do produto
    @PostMapping
    public String salvarProduto(@RequestParam String nome, @RequestParam String marca, 
                                @RequestParam double preco, @RequestParam int estoque, 
                                @RequestParam String descricao, @RequestParam Long fornecedor_id) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setMarca(marca);
        produto.setPreco(preco);
        produto.setEstoque(estoque);
        produto.setDescricao(descricao);
        produto.setFornecedor(fornecedorRepository.findById(fornecedor_id).orElse(null));

        produtoRepository.save(produto);
        return "redirect:/dashboard/produtos";
    }

    // Página de edição de produto
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        model.addAttribute("produto", produto);
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "produto_form";
    }

    // Atualiza as informações do produto
    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @RequestParam String nome, 
                                   @RequestParam String marca, @RequestParam double preco, 
                                   @RequestParam int estoque, @RequestParam String descricao, 
                                   @RequestParam Long fornecedor_id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        produto.setNome(nome);
        produto.setMarca(marca);
        produto.setPreco(preco);
        produto.setEstoque(estoque);
        produto.setDescricao(descricao);
        produto.setFornecedor(fornecedorRepository.findById(fornecedor_id).orElse(null));

        produtoRepository.save(produto);
        return "redirect:/dashboard/produtos";
    }

    // Deleta um produto
    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            return "redirect:/dashboard/produtos?error=true";
        }
        return "redirect:/dashboard/produtos";
    }
}
