package com.example.apontamento.Entity;

import jakarta.persistence.*;

@Entity
public class Funcionario {
    @Id
    private Long matricula;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String turno;

    @Column(nullable = false)
    private String acesso;

    private String senha;

    @Column(nullable = false)
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Funcionario gestor;

    public Funcionario(){}

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Funcionario getGestor() {
        return gestor;
    }

    public void setGestor(Funcionario gestor) {
        this.gestor = gestor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
