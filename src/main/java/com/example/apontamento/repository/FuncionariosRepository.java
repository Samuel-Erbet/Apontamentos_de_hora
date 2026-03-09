package com.example.apontamento.repository;

import com.example.apontamento.Entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionariosRepository extends JpaRepository<Funcionario, Long> {
    // filtra o funcionário pelo nome
    Optional<Funcionario> findByEmail(String nome);
}
