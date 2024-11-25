package br.com.compustock.repository;

import br.com.compustock.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Método para buscar funcionário pelo email (para o login)
    Optional<Funcionario> findByEmail(String email);
}
