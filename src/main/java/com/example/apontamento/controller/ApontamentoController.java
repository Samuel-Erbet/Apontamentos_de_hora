package com.example.apontamento.controller;

import com.example.apontamento.Entity.Apontamentos;
import com.example.apontamento.Entity.ApontamentosForm;
import com.example.apontamento.repository.ApontamentoRepository;
import com.example.apontamento.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.util.List;

@Controller
public class ApontamentoController {
    /*
        // DOCUMENTAR O CÓDIGO
        // CRIAR EXCEÇÕES
        // MEXER NA PARTE DO EMAIL DO FUNCIONÁRIO E TESTAR SE FUNCIONA
        // ADICIONAR O FABIANO NO TESTE
        // MEXER NO FILTRO DO FUNCIONÁRIO REPOSITORY E ADICIONAR A BUSCAR POR ID, ASSIM A ORDEM SERÁ MANTIDA IGUAL.
            INTERESSANTE
            * MEDIR O DESEMPENHO DA APLICAÇÃO

     */
    @Autowired
    private ApontamentoRepository repository;

    @Autowired
    private EmailService enviarEmail;

    @Value("${EMAIL_USER_RECEIVER}") String destinatario;

    @PostMapping("apontamentos/form/save")
    public String saveAPontamento(@ModelAttribute("apontamentos") ApontamentosForm list, Model model) {

        var unidadeGlobal = list.getItens().get(0).getUnidade();
        var dataGlobal = list.getItens().get(0).getData();

        if (unidadeGlobal == null || unidadeGlobal.isBlank() || dataGlobal == null) {
            model.addAttribute("error", "preencha a UNIDADE e a DATA ");
            return "index";
        }

        List<Apontamentos> itensParaSalvar = list.getItens().stream()
                .filter(ap -> ap.getCodigoParada() != null && !ap.getCodigoParada().isBlank())
                .toList();

        if (itensParaSalvar.isEmpty()) {
            model.addAttribute("error", "Preencha pelo menos 4 tarefas na lista abaixo.");
            return "index";
        }

        double totalMinutos = 0;

        for (Apontamentos ap : itensParaSalvar) {
            ap.setUnidade(unidadeGlobal);
            ap.setData(dataGlobal);
            ap.setFuncionario(list.getFuncionario());

            if (ap.getDescricao() == null || ap.getDescricao().isBlank()) {
                model.addAttribute("error", "Faltou a descrição em uma das tarefas");
                return "index";
            }

            if (ap.getHorarioInicio() == null || ap.getHorarioFim() == null) {
                model.addAttribute("error", "Preencha os horários de início e fim");
                return "index";
            }

            if (ap.getHorarioInicio().isAfter(ap.getHorarioFim())) {
                model.addAttribute("error", "Horário de início não pode ser maior que o fim");
                return "index";
            }

            totalMinutos += Duration.between(ap.getHorarioInicio(), ap.getHorarioFim()).toMinutes();
        }

        if (totalMinutos < 480) {
            model.addAttribute("error", "A soma das horas deu " + totalMinutos + " min. Precisa de pelo menos 480 (8h)");
            model.addAttribute("apontamentos", list);
            return "index";
        }

        repository.saveAll(itensParaSalvar);
        // lógica que pega o usuário do gestor e envia a menssagem pelo email dele
        try {
            enviarEmail.enviarEmail(list, list.getFuncionario().getGestor().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/success";
    }


    @GetMapping("/success")
    public String sucesso(){
        return "success";
    }




}
