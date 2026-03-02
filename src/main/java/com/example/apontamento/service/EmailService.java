package com.example.apontamento.service;

import com.example.apontamento.Entity.Apontamentos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// Essa classe vai ser responsável por disparar um email toda vez que um apontamento for feito
@Service
public class EmailService {
    @Autowired
    private JavaMailSender enviarEmail;

    @Value("${spring.mail.username}")
    private String remetente;

    // Metodo que vai disparar o email toda vez que alguém fazer apontamento
    public String enviarEmailTexto(Apontamentos apontamento, String destinatario){
        String menssagem = "O usuário "+apontamento.getFuncionario().getNome()
                +" fez o apontamento do dia "+apontamento.getData();

        String assunto = "Apontamento do dia "+apontamento.getData()+" de "
                +apontamento.getFuncionario().getNome();

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);  // quem vai mandar
            simpleMailMessage.setTo(destinatario); // quem vai receber
            simpleMailMessage.setSubject(assunto); // assunto
            simpleMailMessage.setText(menssagem);  // menssagem de texto
            enviarEmail.send(simpleMailMessage);
            return "Email enviado";
        } catch (Exception e) {
            e.printStackTrace();
            return "erro ao tentar enviar email";
        }

    }
}
