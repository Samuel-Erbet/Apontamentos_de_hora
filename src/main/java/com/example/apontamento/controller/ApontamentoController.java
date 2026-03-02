package com.example.apontamento.controller;

import com.example.apontamento.Entity.Apontamentos;
import com.example.apontamento.Entity.ApontamentosForm;
import com.example.apontamento.Entity.Funcionario;
import com.example.apontamento.repository.ApontamentoRepository;
import com.example.apontamento.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ApontamentoController {
    /*
        // DOCUMENTAR O CÓDIGO
        // CRIAR UM SCRIPT QUE ENVIE UM EMAIL AO CHEFE SEMPRE QUE UM USUÁRIO ENVIAR UM APONTAMENTO
        // PRECISO TESTAR SE A FUNÇÃO MANDAR EMAIL FUNCIONA
        // PRECISO CRIAR UMA COLUNA QUE ASSOCIE UM GESTOR A SEU FUNCIONÁRIO
        // CRIAR COLUNA EMAIL
     */
    @Autowired
    private ApontamentoRepository repository;

    @Autowired
    private EmailService enviarEmail;

    @PostMapping("apontamentos/form/save")
    public String saveAPontamento(@ModelAttribute("apontamentos") ApontamentosForm list, Model model) {

        var unidadeGlobal = list.getItens().get(0).getUnidade();
        var dataGlobal = list.getItens().get(0).getData();

        if (unidadeGlobal == null || unidadeGlobal.isBlank() || dataGlobal == null) {
            model.addAttribute("error", "Ô fera, preencha a UNIDADE e a DATA lá no topo!");
            return "index";
        }

        List<Apontamentos> itensParaSalvar = list.getItens().stream()
                .filter(ap -> ap.getCodigoParada() != null && !ap.getCodigoParada().isBlank())
                .toList();

        if (itensParaSalvar.isEmpty()) {
            model.addAttribute("error", "Preencha pelo menos UMA tarefa na lista abaixo.");
            return "index";
        }

        double totalMinutos = 0;

        for (Apontamentos ap : itensParaSalvar) {
            ap.setUnidade(unidadeGlobal);
            ap.setData(dataGlobal);
            ap.setFuncionario(list.getFuncionario());

            if (ap.getDescricao() == null || ap.getDescricao().isBlank()) {
                model.addAttribute("error", "Faltou a descrição em uma das tarefas!");
                return "index";
            }

            if (ap.getHorarioInicio() == null || ap.getHorarioFim() == null) {
                model.addAttribute("error", "Preencha os horários de início e fim!");
                return "index";
            }

            if (ap.getHorarioInicio().isAfter(ap.getHorarioFim())) {
                model.addAttribute("error", "Horário de início não pode ser maior que o fim, né?");
                return "index";
            }

            totalMinutos += Duration.between(ap.getHorarioInicio(), ap.getHorarioFim()).toMinutes();
        }

        if (totalMinutos < 480) {
            model.addAttribute("error", "A soma das horas deu " + totalMinutos + " min. Precisa de pelo menos 480 (8h)!");
            model.addAttribute("apontamentos", list);
            return "index";
        }

        repository.saveAll(itensParaSalvar);
        // lógica que pega o usuário do gestor e envia a menssagem pelo email dele

        //enviarEmail.enviarEmailTexto(list.getItens().get(0),"${EMAIL_USER_RECEIVER}");

        return "redirect:/success";
    }


    @GetMapping("/success")
    public String sucesso(){
        return "success";
    }


 /*
    @GetMapping("/disparar-email-teste")
    @ResponseBody
    public String dispararEmailTeste() {
        try {
            // Use o nome EXATO do metodo que você criou no seu EmailService

            // 1. Criar e configurar o Funcionário (quem está fazendo o apontamento)
            Funcionario func = new Funcionario();
            func.setMatricula(12345L);
            func.setNome("Samuel Erbet");

// 2. Criar o objeto Apontamentos e preencher via Setters
            Apontamentos testeApontamento = new Apontamentos();
            testeApontamento.setFuncionario(func);
            testeApontamento.setData(LocalDate.now());
            testeApontamento.setCodigoParada("P-10");
            testeApontamento.setNumeroOs("OS-9988");
            testeApontamento.setHorarioInicio(LocalTime.of(8, 00));
            testeApontamento.setHorarioFim(LocalTime.of(17, 00));
            testeApontamento.setUnidade("Unidade Central");
            testeApontamento.setDescricao("Teste de envio de e-mail automatizado");

// 3. Chamar o serviço de e-mail (supondo que o destinatário venha da sua config)
            String resultado = enviarEmail.enviarEmailTexto(testeApontamento, "samuelerbet@gmail.com");
            System.out.println(resultado);
            return resultado;
        } catch (Exception e) {
            return "<h1>❌ Erro no envio</h1><p>Detalhe: " + e.getMessage() + "</p>";
        }
    }
*/
}
