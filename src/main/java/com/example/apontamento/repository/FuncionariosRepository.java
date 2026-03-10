package com.example.apontamento.repository;

import com.example.apontamento.Entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FuncionariosRepository extends JpaRepository<Funcionario, Long> {
    // filtra o funcionário pelo nome
    Optional<Funcionario> findByEmail(String nome);

    @Query("SELECT f FROM Funcionario f JOIN FETCH f.gestor WHERE f.matricula = :matricula")
    Optional<Funcionario> findByMatriculaWithGestor(@Param("matricula") Long matricula);
}
