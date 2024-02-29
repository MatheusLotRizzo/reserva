package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

public class ExcluirUsuario {
    private final UsuarioRepository repository;

    public ExcluirUsuario(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public void executar(final String email){
        repository.excluir(new EmailVo(email));
    }
}
