package com.example.apontamento.service;

import brevo.ApiClient;
import com.example.apontamento.Entity.Apontamentos;
import com.example.apontamento.Entity.ApontamentosForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Essa classe vai ser responsável por disparar um email toda vez que um apontamento for feito
@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Async
    public void enviarEmail(ApontamentosForm form, String emailGestor) {
        String url = "https://api.brevo.com/v3/smtp/email";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        String nomeFuncionario = form.getFuncionario().getNome();
        String dataTitulo = form.getItens().isEmpty() ? "N/A" : form.getItens().get(0).getData().toString();

        // Montando as linhas da tabela (os "registros" da planilha)
        StringBuilder linhasTabela = new StringBuilder();
        for (Apontamentos item : form.getItens()) {
            linhasTabela.append(String.format("""
                <tr>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">%s</td>
                </tr>
                """,
                    form.getFuncionario().getMatricula(),
                    item.getData(),
                    item.getCodigoParada(),
                    item.getNumeroOs(),
                    item.getDescricao(),
                    item.getHorarioInicio(),
                    item.getHorarioFim()
            ));
        }

        // Estrutura da Tabela (Cabeçalho igual ao da sua imagem)
        String corpoHtml = """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2 style="color: #333;">Relatório de Apontamentos - %s</h2>
                    <table style="width: 100%%; border-collapse: collapse; font-size: 12px;">
                        <thead>
                            <tr style="background-color: #f2f2f2; text-align: left;">
                                <th style="border: 1px solid #ddd; padding: 8px;">Matrícula</th>
                                <th style="border: 1px solid #ddd; padding: 8px;">Data</th>
                                <th style="border: 1px solid #ddd; padding: 8px;">Cód. Parada</th>
                                <th style="border: 1px solid #ddd; padding: 8px;">Nº OS</th>
                                <th style="border: 1px solid #ddd; padding: 8px;">Descrição</th>
                                <th style="border: 1px solid #ddd; padding: 8px;">Início</th>
                                <th style="border: 1px solid #ddd; padding: 8px;">Fim</th>
                            </tr>
                        </thead>
                        <tbody>
                            %s
                        </tbody>
                    </table>
                    <br>
                    <p style="color: #666; font-size: 11px;">Gerado automaticamente pelo Sistema de Apontamentos.</p>
                </body>
                </html>
                """.formatted(nomeFuncionario, linhasTabela.toString());

        Map<String, Object> payload = new HashMap<>();
        payload.put("sender", Map.of("name", "Sistema KRCB", "email", "apontamentoskrcb@gmail.com"));
        payload.put("to", List.of(Map.of("email", emailGestor)));
        payload.put("subject", "Apontamento: " + nomeFuncionario + " | " + dataTitulo);
        payload.put("htmlContent", corpoHtml);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForEntity(url, entity, String.class);
            System.out.println("Planilha enviada para: " + emailGestor);
        } catch (Exception e) {
            System.err.println("Erro ao enviar planilha: " + e.getMessage());
        }
    }
}