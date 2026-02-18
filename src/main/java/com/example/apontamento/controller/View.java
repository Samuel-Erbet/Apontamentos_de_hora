package com.example.apontamento.controller;

import com.example.apontamento.Entity.Apontamentos;
import com.example.apontamento.Entity.ApontamentosForm;
import com.example.apontamento.Entity.Funcionario;
import com.example.apontamento.repository.ApontamentoRepository;
import com.example.apontamento.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Collections;
import java.util.List;

@Controller
public class View {

    @Autowired
    private ApontamentoRepository repository;

    @Autowired
    private FuncionariosRepository funcionariosRepository;

    @GetMapping("/apontamentos")

    public ModelAndView mostrarFormulario(Authentication authentication) {

        ModelAndView mv = new ModelAndView("index");

        String nomeLogado = authentication.getName();
        Funcionario funcionarioLogado = funcionariosRepository.findByNome(nomeLogado)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        var apontamentosForm = new ApontamentosForm();
        apontamentosForm.setFuncionario(funcionarioLogado);
        mv.addObject("apontamentos", apontamentosForm);

        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }


    @GetMapping("/apontamentos/meus-apontamentos")
    public ModelAndView meusApontamentos(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()){
            return  new ModelAndView("redirect:/login");
        }

        String user = authentication.getName();

        Funcionario funcionario = funcionariosRepository.findByNome(user).orElse(null);
        List<Apontamentos> lista = repository.findByFuncionarioNome(user);

        if (lista == null){
            lista = Collections.emptyList();
        }

        ModelAndView mv = new ModelAndView("meusApontamentos");
        mv.addObject("apontamentos", lista);
        mv.addObject("funcionario", funcionario);
        return mv;

    }
}
