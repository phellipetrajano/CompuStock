package br.com.compustock.repository;

import br.com.compustock.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {}
