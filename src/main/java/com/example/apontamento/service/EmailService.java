package com.example.apontamento.service;

import com.example.apontamento.Entity.ApontamentosForm;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

// Essa classe vai ser responsável por disparar um email toda vez que um apontamento for feito
@Service
@Async
public class EmailService {
    @Autowired
    private JavaMailSender enviarEmail;

    @Value("${spring.mail.username}")
    private String remetente;

    // Metodo que vai disparar o email toda vez que alguém fazer apontamento
    public String enviarEmailTexto(ApontamentosForm apontamento, String destinatario){
        MimeMessage mimeMessage = enviarEmail.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String nomeFuncionario = apontamento.getFuncionario().getNome();
            String dataApontamento = apontamento.getItens().get(0).getData().toString();

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Apontamento: " + nomeFuncionario + " - " + dataApontamento);

            StringBuilder html = new StringBuilder();
            html.append("<h2 style='color: #217346;'>Relatório de Apontamento</h2>");
            html.append("<p><b>Funcionário:</b> ").append(nomeFuncionario).append("</p>");

            html.append("<table border='1' style='border-collapse: collapse; width: 100%; font-family: Calibri, Arial, sans-serif;'>");
            html.append("<tr style='background-color: #217346; color: white; text-align: center;'>");
            html.append("<th style='padding: 10px;'>Data</th>");
            html.append("<th style='padding: 10px;'>Cód. Parada</th>");
            html.append("<th style='padding: 10px;'>Número OS</th>");
            html.append("<th style='padding: 10px;'>Descrição do Trabalho</th>");
            html.append("<th style='padding: 10px;'>Horário Início</th>");
            html.append("<th style='padding: 10px;'>Horário Fim</th>");
            html.append("</tr>");

            for (var item : apontamento.getItens()) {
                html.append("<tr style='text-align: center;'>");
                html.append("<td style='padding: 8px;'>").append(item.getData()).append("</td>");
                html.append("<td style='padding: 8px;'>").append(item.getCodigoParada()).append("</td>");
                html.append("<td style='padding: 8px;'>").append(item.getNumeroOs()).append("</td>");
                html.append("<td style='padding: 8px; text-align: left;'>").append(item.getDescricao()).append("</td>");
                html.append("<td style='padding: 8px;'>").append(item.getHorarioInicio()).append("</td>");
                html.append("<td style='padding: 8px;'>").append(item.getHorarioFim()).append("</td>");
                html.append("</tr>");
            }

            html.append("</table>");
            html.append("<p style='font-size: 12px; color: #666;'>Gerado pelo Sistema de Apontamentos KRCB</p>");

            helper.setText(html.toString(), true);

            enviarEmail.send(mimeMessage);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "erro ao tentar enviar email";
        }

    }
}
