package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class CadastrarUsuario {
    private final UsuarioRepository repository;

    public CadastrarUsuario(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public Usuario executar(Usuario usuario) throws BusinessException {
        if(new BuscarUsuario(repository).getUsuario(usuario) == null){
            throw new BusinessException("Usuário não pode ser cadastrado, pois já existe");
        }
        return this.repository.cadastrar(usuario);
    }
}
