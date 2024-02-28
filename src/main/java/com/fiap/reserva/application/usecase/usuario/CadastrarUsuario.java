package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class CadastrarUsuario {
    private final UsuarioRepository repository;

    public CadastrarUsuario(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public void executar(Usuario usuario){
        this.repository.cadastrar(usuario);
    }
}
