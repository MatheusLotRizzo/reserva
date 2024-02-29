package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.usuario.BuscarUsuario;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getBuscarPorEmail(final EmailVo email) throws BusinessException{
        return new BuscarUsuario(repository).getUsuarioPor(email);
    }
}
