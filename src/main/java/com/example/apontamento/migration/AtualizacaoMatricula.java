package com.example.apontamento.migration;

import com.example.apontamento.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AtualizacaoMatricula implements CommandLineRunner {

    @Autowired
    private FuncionariosRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        repository.findAll().forEach(funcionario -> {
            if (funcionario.getSenha() == null) {
                String hash = encoder.encode(funcionario.getMatricula().toString());
                funcionario.setSenha(hash);
                repository.save(funcionario);
                System.out.println("Senha do " + funcionario.getNome() + " atualizada!");
            }
        });
    }
}