package br.com.compustock.controller;

import br.com.compustock.model.Fornecedor;
import br.com.compustock.model.Produto;
import br.com.compustock.repository.FornecedorRepository;
import br.com.compustock.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    @Transactional
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoRepository.findAllWithFornecedor();  // Usando o método otimizado com JOIN FETCH
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @GetMapping("/novo")
    public String novoProduto(Model model) {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        model.addAttribute("fornecedores", fornecedores);
        model.addAttribute("produto", new Produto());
        return "produto_form";
    }

    @PostMapping
    public String salvarProduto(@ModelAttribute Produto produto, @RequestParam Long fornecedorId) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElseThrow();
        produto.setFornecedor(fornecedor);
        produtoRepository.save(produto);
        return "redirect:/dashboard/produtos"; // Redireciona de volta para a lista de produtos
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        model.addAttribute("produto", produto);
        model.addAttribute("fornecedores", fornecedores);
        return "produto_form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @ModelAttribute Produto produto, @RequestParam Long fornecedorId) {
        Produto produtoExistente = produtoRepository.findById(id).orElseThrow();
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElseThrow();
        
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setMarca(produto.getMarca());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setEstoque(produto.getEstoque());
        produtoExistente.setFornecedor(fornecedor);
        
        produtoRepository.save(produtoExistente);
        return "redirect:/dashboard/produtos"; // Redireciona de volta para a lista de produtos
    }

    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/dashboard/produtos"; // Redireciona de volta para a lista de produtos
    }
}
