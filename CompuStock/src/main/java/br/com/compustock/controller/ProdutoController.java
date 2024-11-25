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
        model.addAttribute("fornecedores", fornecedorRepository.findAll()); // Carregar todos os fornecedores
        return "produto_form"; // Página de cadastro de produto
    }

    // Processa o cadastro do produto
    @PostMapping
    public String salvarProduto(@RequestParam String nome, @RequestParam String marca, 
                                @RequestParam double preco, @RequestParam int estoque, 
                                @RequestParam Long fornecedor_id) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setMarca(marca);
        produto.setPreco(preco);
        produto.setEstoque(estoque);
        
        // Garantir que o fornecedor esteja associado corretamente
        produto.setFornecedor(fornecedorRepository.findById(fornecedor_id).orElseThrow()); 

        produtoRepository.save(produto); // Salva no banco de dados
        return "redirect:/dashboard/produtos"; // Redireciona para a página de listagem de produtos
    }

    // Página de edição de produto
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        model.addAttribute("produto", produto);
        model.addAttribute("fornecedores", fornecedorRepository.findAll()); // Carregar fornecedores para edição
        return "produto_form"; // Página de edição de produto
    }

    // Atualiza as informações de um produto
    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @RequestParam String nome, @RequestParam String marca,
                                   @RequestParam double preco, @RequestParam int estoque, 
                                   @RequestParam Long fornecedor_id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        produto.setNome(nome);
        produto.setMarca(marca);
        produto.setPreco(preco);
        produto.setEstoque(estoque);
        
        // Atualiza o fornecedor associado
        produto.setFornecedor(fornecedorRepository.findById(fornecedor_id).orElseThrow());

        produtoRepository.save(produto); // Atualiza no banco de dados
        return "redirect:/dashboard/produtos"; // Redireciona para a página de listagem de produtos
    }

    // Deleta um produto
    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id); // Deleta o produto do banco de dados
        return "redirect:/dashboard/produtos"; // Redireciona para a página de listagem de produtos
    }
}
