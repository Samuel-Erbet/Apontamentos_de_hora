package com.example.apontamento.repository;

import com.example.apontamento.Entity.Apontamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ApontamentoRepository extends JpaRepository<Apontamentos,Long> {
    List<Apontamentos> findByFuncionarioNome(String nome);
}