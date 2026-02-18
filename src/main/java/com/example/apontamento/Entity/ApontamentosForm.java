package com.example.apontamento.Entity;

import java.util.ArrayList;
import java.util.List;

public class ApontamentosForm {

    private Funcionario funcionario;
    private List<Apontamentos> itens = new ArrayList<>();

    public ApontamentosForm() {
        this.itens.add(new Apontamentos());
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Apontamentos> getItens() {
        return itens;
    }

    public void setItens(List<Apontamentos> itens) {
        this.itens = itens;
    }
}

