package com.example.apontamento.Entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Apontamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "funcionario_matricula" )
    private Funcionario funcionario ;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @Column(nullable = false)
    private String codigoParada;

    private String numeroOs;

    @Column(nullable = false)
    private LocalTime horarioInicio;

    @Column(nullable = false)
    private LocalTime horarioFim;

    @Column(nullable = false)
    private String unidade;

    @Column(nullable = false)
    private String descricao;

    @UpdateTimestamp
    private LocalTime ultimaAlteracao;


    public Apontamentos() {};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCodigoParada() {
        return codigoParada;
    }

    public void setCodigoParada(String codigoParada) {
        this.codigoParada = codigoParada;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalTime getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public void setUltimaAlteracao(LocalTime ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
    }
}
