package br.com.compustock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.compustock.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p JOIN FETCH p.fornecedor")
    List<Produto> findAllWithFornecedor();
}
