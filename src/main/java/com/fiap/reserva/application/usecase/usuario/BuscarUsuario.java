package com.fiap.reserva.application.usecase.usuario;

import java.util.List;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

public class BuscarUsuario {
    private final UsuarioRepository repository;

    public BuscarUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getUsuario(EmailVo emailVo) throws BusinessException {
        if (emailVo == null) {
            throw new BusinessException("Email é obrigatorio para realizar a busca!");
        }
        return repository.buscarPor(emailVo);
    }

    public List<Usuario> getTodos()throws BusinessException{
        return repository.buscarTodos();
    }
}
