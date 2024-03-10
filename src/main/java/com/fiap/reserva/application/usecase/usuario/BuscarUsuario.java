package com.fiap.reserva.application.usecase.usuario;

import java.util.List;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class BuscarUsuario {
    private final UsuarioRepository repository;

    public BuscarUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getUsuario(Usuario usuario) throws BusinessException {
        if (usuario == null) {
            throw new BusinessException("Usuario é obrigatorio para realizar a busca!");
        }
        return repository.buscarPor(usuario);
    }

    public List<Usuario> getTodos(Usuario usuario)throws BusinessException{
        return repository.buscarTodos(usuario);
    }

}
