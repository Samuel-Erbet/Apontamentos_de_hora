package com.example.apontamento.security;

import com.example.apontamento.Entity.Funcionario;
import com.example.apontamento.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// Essa classe serve para setar as condições de login, se for a primeira vez do user ele vai criar a senha dele através
// do hash da matricula

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private FuncionariosRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String nomeDigitado) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByNome(nomeDigitado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + nomeDigitado));

        if (funcionario.getSenha() == null || funcionario.getSenha().isEmpty()) {
            String hashMatricula = encoder.encode(funcionario.getMatricula().toString());
            funcionario.setSenha(hashMatricula);
            funcionarioRepository.saveAndFlush(funcionario);
            System.out.println("Senha criada para: " + nomeDigitado);
        }

        return User.withUsername(funcionario.getMatricula().toString())
                .password(funcionario.getSenha())
                .roles(funcionario.getAcesso())
                .build();
    }
}