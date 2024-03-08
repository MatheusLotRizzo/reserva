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
        if (usuario == null) {
            throw new BusinessException("Usuario é obrigatório para realizar o cadastro!");
        }
        return this.repository.cadastrar(usuario);
    }
}