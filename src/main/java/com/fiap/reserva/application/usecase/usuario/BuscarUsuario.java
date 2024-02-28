package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;

import java.util.List;

public class BuscarUsuario {
    private final UsuarioRepository repository;

    public BuscarUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getUsuario(Usuario usuario)throws BusinessException {
        return repository.buscar(usuario);
    }

    public Usuario getUsuarioPor(String email)throws BusinessException{
        return repository.buscar(new Usuario(email));
    }

    public List<Usuario> getTodos(Usuario usuario)throws BusinessException{
        return repository.buscarTodos(usuario);
    }

}
