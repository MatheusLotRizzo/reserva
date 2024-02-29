package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.usuario.AlterarUsuario;
import com.fiap.reserva.application.usecase.usuario.BuscarUsuario;
import com.fiap.reserva.application.usecase.usuario.CadastrarUsuario;
import com.fiap.reserva.application.usecase.usuario.ExcluirUsuario;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario cadastrar(final Usuario usuario){
        return new CadastrarUsuario(repository).executar(usuario);
    }

    public Usuario alterar(final Usuario usuario) throws BusinessException {
        return new AlterarUsuario(repository).executar(usuario);
    }

    public void excluir(final EmailVo email){
        new ExcluirUsuario(repository).executar(email);
    }

    public Usuario getBuscarPor(final Usuario usuario) throws BusinessException{
        return new BuscarUsuario(repository).getUsuario(usuario);
    }

    public Usuario getBuscarPorEmail(final EmailVo email) throws BusinessException{
        return new BuscarUsuario(repository).getUsuarioPor(email);
    }
}