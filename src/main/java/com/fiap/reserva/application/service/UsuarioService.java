package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.usuario.AlterarUsuario;
import com.fiap.reserva.application.usecase.usuario.BuscarUsuario;
import com.fiap.reserva.application.usecase.usuario.CadastrarUsuario;
import com.fiap.reserva.application.usecase.usuario.ExcluirUsuario;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

import java.util.List;

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario cadastrar(final Usuario usuario) throws BusinessException{
        final Usuario usuarioBuscaBanco = new BuscarUsuario(repository).getUsuario(usuario);
		if(usuario.equals(usuarioBuscaBanco)){
            throw new BusinessException("Usuário não pode ser cadastrado, pois já existe");
        }
        return new CadastrarUsuario(repository).executar(usuario);
    }

    public Usuario alterar(final Usuario usuario) throws BusinessException {
        if(new BuscarUsuario(repository).getUsuario(usuario) == null){
            throw new BusinessException("Usuário não pode ser alterado, pois nao foi encontrada");
        }
        return new AlterarUsuario(repository).executar(usuario);
    }

    public void excluir(final EmailVo email) throws BusinessException{
        if(new BuscarUsuario(repository).getUsuario(new Usuario(email.getEndereco())) == null){
            throw new BusinessException("Usuário não pode ser excluido, pois nao foi encontrada");
        }
        new ExcluirUsuario(repository).executar(email);
    }

    public Usuario getBuscarPor(final Usuario usuario) throws BusinessException{
        return new BuscarUsuario(repository).getUsuario(usuario);
    }

    public List<Usuario> getTodos(final Usuario usuario) throws BusinessException{
        return new BuscarUsuario(repository).getTodos(usuario);
    }
}
