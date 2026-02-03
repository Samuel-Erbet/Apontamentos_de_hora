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


@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private FuncionariosRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String nomeDigitado) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByNome(nomeDigitado)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado: " + nomeDigitado));

        String senhaCriptografada =encoder.encode(funcionario.getMatricula().toString());

        return User.withUsername(funcionario.getNome())
                .password(senhaCriptografada)
                .roles(funcionario.getAcesso())
                .build();
    }
}
