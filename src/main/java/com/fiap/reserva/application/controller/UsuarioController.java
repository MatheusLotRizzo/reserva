package com.fiap.reserva.application.controller;

import com.fiap.reserva.application.usecase.usuario.AlterarUsuario;
import com.fiap.reserva.application.usecase.usuario.BuscarUsuario;
import com.fiap.reserva.application.usecase.usuario.CadastrarUsuario;
import com.fiap.reserva.application.usecase.usuario.ExcluirUsuario;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public void cadastrar(final String nome, final String email, final String celular){
        final Usuario usuario = construirUsuario(nome,email,celular);

        new CadastrarUsuario(repository).executar(usuario);
    }

    public void alterar(final String nome, final String email, final String celular) throws BusinessException {
        final Usuario usuario = construirUsuario(nome,email,celular);

        new AlterarUsuario(repository).executar(usuario);
    }

    public void excluir(final String email){
        new ExcluirUsuario(repository).executar(email);
    }

    public Usuario getBuscarPor(final String nome, final String email, final String celular) throws BusinessException{
        return new BuscarUsuario(repository).getUsuario(construirUsuario(nome,email,celular));
    }

    public Usuario getBuscarPorEmail(final String email) throws BusinessException{
        return new BuscarUsuario(repository).getUsuarioPor(email);
    }

    private Usuario construirUsuario(final String nome, final String email, final String celular) {
        return new Usuario(nome, email, celular);
    }
}
