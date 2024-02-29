package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class AlterarUsuario {
    private final UsuarioRepository repository;

    public AlterarUsuario(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public Usuario executar(Usuario usuario) throws BusinessException {
        if(usuario == null){
            throw new BusinessException("Usuario é obrigatorio");
        }

        if(new BuscarUsuario(repository).getUsuario(usuario) == null){
            throw new BusinessException("Usuário não pode ser alterado, pois nao foi encontrada");
        }

        return repository.alterar(usuario);
    }
}
