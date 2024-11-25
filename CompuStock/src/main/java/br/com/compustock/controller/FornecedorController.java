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
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "fornecedores"; // Página de listagem de fornecedores
    }

    // Página de cadastro de fornecedor
    @GetMapping("/novo")
    public String cadastrarFornecedor() {
        return "fornecedor_form"; // Página de cadastro de fornecedor
    }

    // Processa o cadastro do fornecedor
    @PostMapping
    public String salvarFornecedor(@RequestParam String nome, @RequestParam String cnpj, 
                                   @RequestParam String email, @RequestParam String telefone) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj); // Salva o CNPJ
        fornecedor.setEmail(email); // Salva o email
        fornecedor.setTelefone(telefone);
        
        fornecedorRepository.save(fornecedor); // Salva no banco de dados
        return "redirect:/dashboard/fornecedores"; // Redireciona para a página de listagem de fornecedores
    }

    // Página de edição de fornecedor
    @GetMapping("/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor_form"; // Página de edição de fornecedor
    }

    // Atualiza as informações de um fornecedor
    @PostMapping("/editar/{id}")
    public String atualizarFornecedor(@PathVariable Long id, @RequestParam String nome, @RequestParam String cnpj,
                                      @RequestParam String email, @RequestParam String telefone) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj); // Atualiza o CNPJ
        fornecedor.setEmail(email); // Atualiza o email
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
            // Se ocorrer um erro ao deletar, redireciona com erro
            return "redirect:/dashboard/fornecedores?error=true"; // Redireciona para página com erro
        }
        return "redirect:/dashboard/fornecedores"; // Redireciona para a página de listagem de fornecedores
    }
}
