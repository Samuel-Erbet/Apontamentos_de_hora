package com.example.apontamento.repository;

import com.example.apontamento.Entity.Apontamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ApontamentoRepository extends JpaRepository<Apontamentos,Long> {
    // filtra os primeiros 60 apontamentos baseado no nome e ordenando por data em forma decrescente e horario de forma crescente
    List<Apontamentos> findTop60ByFuncionarioMatriculaOrderByDataDescHorarioInicioAsc(Long matricula);
}