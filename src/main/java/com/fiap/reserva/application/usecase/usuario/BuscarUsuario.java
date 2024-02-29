package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

import java.util.List;

public class BuscarUsuario {
    private final UsuarioRepository repository;

    public BuscarUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getUsuario(Usuario usuario)throws BusinessException {
        return repository.buscar(usuario);
    }

    public Usuario getUsuarioPor(EmailVo email)throws BusinessException{
        return repository.buscarPorEmail(email);
    }

    public List<Usuario> getTodos(Usuario usuario)throws BusinessException{
        return repository.buscarTodos(usuario);
    }

}
