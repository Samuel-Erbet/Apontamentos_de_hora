package com.example.apontamento.controller;

import com.example.apontamento.Entity.Apontamentos;
import com.example.apontamento.Entity.ApontamentosForm;
import com.example.apontamento.repository.ApontamentoRepository;
import com.example.apontamento.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.Duration;
import java.util.List;

@Controller
public class ApontamentoController {

    @Autowired
    private ApontamentoRepository repository;


    @PostMapping("apontamentos/form/save")
    public String saveAPontamento(
            @ModelAttribute("apontamentos") ApontamentosForm list,
            Model model,
            RedirectAttributes redirect
    ){
        var unidade = list.getItens().get(0).getUnidade();
        var  data = list.getItens().get(0).getData();

        if (list.getFuncionario()==null || list.getFuncionario().getNome() == null || list.getFuncionario().getMatricula() == null || list.getFuncionario().getTurno() == null){
            model.addAttribute("error", "preencha as informações necessárias.");
            return "index";
        }

        List<Apontamentos> itensParaSalvar = list.getItens().stream()
                .filter(ap -> ap.getCodigoParada() != null && !ap.getCodigoParada().isBlank())
                .toList();

        if (itensParaSalvar.isEmpty()) {
            model.addAttribute("error", "Preencha pelo menos uma tarefa.");
            return "index";
        }

        double totalHorasValidas = 0;
        for(Apontamentos ap : list.getItens() ){
            if (ap.getCodigoParada() == null){
                model.addAttribute("error", "preencha o código de parada.");
                return "index";
            } else if (ap.getData() == null){
                model.addAttribute("error", "preencha a data.");
                return "index";
            } else if (ap.getHorarioInicio().isAfter(ap.getHorarioFim())){
                model.addAttribute("error", "preencha o horário corretamente.");
                return "index";
            } else if (ap.getDescricao() == null){
                model.addAttribute("error", "preencha a descrição.");
                return "index";
            } else if (ap.getCodigoParada().equalsIgnoreCase("1b") && ap.getNumeroOs() == null){
                model.addAttribute("error", "preencha o número da Os.");
                return "index";
            }
            totalHorasValidas += Duration.between(ap.getHorarioInicio(), ap.getHorarioFim()).toMinutes();
        }


        if (totalHorasValidas < 480){
            model.addAttribute("error", "Horários inválidos.");
            return "index";
        }

        for (Apontamentos ap : list.getItens()) {
            ap.setUnidade(unidade);
            ap.setData(data);
            ap.setFuncionario(list.getFuncionario());
        }

        repository.saveAll(itensParaSalvar);

        return "redirect:/success";
    }


    @GetMapping("/success")
    public String sucesso(){
        return "success";
    }


    @GetMapping("/menu")
    public String menu(){
        return "menu";
    }

}
