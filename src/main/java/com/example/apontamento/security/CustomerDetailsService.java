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
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private FuncionariosRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String emailDigitado) throws UsernameNotFoundException {

        // Busca o funcionário pelo e-mail
        Funcionario funcionario = funcionarioRepository.findByEmail(emailDigitado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com e-mail não encontrado: " + emailDigitado));

        // gera uma senha caso ela não exista
        if (funcionario.getSenha() == null || funcionario.getSenha().isEmpty()) {
            String hashMatricula = encoder.encode(funcionario.getMatricula().toString());
            funcionario.setSenha(hashMatricula);
            funcionarioRepository.saveAndFlush(funcionario);
        }

        // Retorna o UserDetails. Note que mantemos a Matrícula ou o Email como "username" interno do Spring Security
        return User.withUsername(funcionario.getEmail())
                .password(funcionario.getSenha())
                .roles(funcionario.getAcesso())
                .build();
    }
}