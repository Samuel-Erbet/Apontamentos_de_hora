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
    public UserDetails loadUserByUsername(String loginDigitado) throws UsernameNotFoundException {

        Funcionario funcionario = funcionarioRepository.findByNome(loginDigitado)
                .or(() -> {
                    try {
                        return funcionarioRepository.findById(Long.parseLong(loginDigitado));
                    } catch (NumberFormatException e) {
                        return java.util.Optional.empty();
                    }
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + loginDigitado));

        if (funcionario.getSenha() == null || funcionario.getSenha().isEmpty()) {
            String hashMatricula = encoder.encode(funcionario.getMatricula().toString());
            funcionario.setSenha(hashMatricula);

            funcionarioRepository.saveAndFlush(funcionario);
            System.out.println( "Senha  gerada para matrícula: " + funcionario.getMatricula());
        }

        return User.withUsername(funcionario.getMatricula().toString())
                .password(funcionario.getSenha())
                .roles(funcionario.getAcesso())
                .build();
    }
}