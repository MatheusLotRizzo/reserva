package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class BuscarUsuario {
    private final UsuarioRepository repository;

    public BuscarUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getUsuario(Usuario usuario){
        return repository.buscar(usuario);
    }

    public Usuario getUsuarioPor(String email){
        return repository.buscar(new Usuario(email));
    }

}
