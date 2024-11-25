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

    // Página de listagem de fornecedores
    @GetMapping
    public String listarFornecedores(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll()); // Passa a lista de fornecedores para a view
        return "fornecedores"; // Página de listagem de fornecedores
    }

    // Página de cadastro de fornecedor
    @GetMapping("/novo")
    public String cadastrarFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor()); // Passa um objeto fornecedor vazio para a view
        return "fornecedor_form"; // Página de cadastro de fornecedor
    }

    // Processa o cadastro do fornecedor
    @PostMapping
    public String salvarFornecedor(@RequestParam String nome, @RequestParam String cnpj, 
                                   @RequestParam String email, @RequestParam String telefone) {
        // Validação simples dos dados
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (cnpj == null || cnpj.isEmpty()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio");
        }

        // Criação do fornecedor
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj);
        fornecedor.setEmail(email);
        fornecedor.setTelefone(telefone);
        
        fornecedorRepository.save(fornecedor); // Salva no banco de dados
        return "redirect:/dashboard/fornecedores"; // Redireciona para a página de listagem de fornecedores
    }

    // Página de edição de fornecedor
    @GetMapping("/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        // Busca o fornecedor no banco, caso contrário lança uma exceção
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        model.addAttribute("fornecedor", fornecedor); // Passa os dados do fornecedor para o form de edição
        return "fornecedor_form"; // Página de edição de fornecedor
    }

    // Atualiza as informações de um fornecedor
    @PostMapping("/editar/{id}")
    public String atualizarFornecedor(@PathVariable Long id, @RequestParam String nome, @RequestParam String cnpj,
                                      @RequestParam String email, @RequestParam String telefone) {
        // Validação simples dos dados
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (cnpj == null || cnpj.isEmpty()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio");
        }

        // Atualiza o fornecedor
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj);
        fornecedor.setEmail(email);
        fornecedor.setTelefone(telefone);

        fornecedorRepository.save(fornecedor); // Atualiza no banco de dados
        return "redirect:/dashboard/fornecedores"; // Redireciona para a página de listagem de fornecedores
    }

    // Deleta um fornecedor
    @GetMapping("/deletar/{id}")
    public String deletarFornecedor(@PathVariable Long id) {
        try {
            fornecedorRepository.deleteById(id); // Deleta o fornecedor do banco de dados
        } catch (Exception e) {
            return "redirect:/dashboard/fornecedores?error=true"; // Redireciona com erro se falhar
        }
        return "redirect:/dashboard/fornecedores"; // Redireciona para a página de listagem de fornecedores
    }
}
