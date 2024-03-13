package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.usuario.AlterarUsuario;
import com.fiap.reserva.application.usecase.usuario.BuscarUsuario;
import com.fiap.reserva.application.usecase.usuario.CadastrarUsuario;
import com.fiap.reserva.application.usecase.usuario.ExcluirUsuario;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

import java.util.List;

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario cadastrar(final Usuario usuario) throws BusinessException{
		if(usuario.equals(new BuscarUsuario(repository).getUsuario(usuario.getEmail()))){
            throw new BusinessException("Usuário não pode ser cadastrado, pois já existe");
        }
        return new CadastrarUsuario(repository).executar(usuario);
    }

    public Usuario alterar(final Usuario usuario) throws BusinessException {
        if(new BuscarUsuario(repository).getUsuario(usuario.getEmail()) == null){
            throw new BusinessException("Usuário não pode ser alterado, pois nao foi encontrada");
        }
        return new AlterarUsuario(repository).executar(usuario);
    }

    public void excluir(final EmailVo email) throws BusinessException{
        if(new BuscarUsuario(repository).getUsuario(email) == null){
            throw new BusinessException("Usuário não pode ser excluido, pois nao foi encontrada");
        }
        new ExcluirUsuario(repository).executar(email);
    }

    public Usuario getBuscarPor(final EmailVo emailVo) throws BusinessException{
        Usuario usuario = new BuscarUsuario(repository).getUsuario(emailVo);
        if(usuario == null){
            throw new EntidadeNaoEncontrada("Usuário não encontrado!");
        }
        return usuario;
    }

    public List<Usuario> getTodos() throws BusinessException{
        List<Usuario> usuarios = new BuscarUsuario(repository).getTodos();
        if(usuarios.isEmpty()){
            throw new EntidadeNaoEncontrada("Nenhum usuário encontrado!");
        }
        return usuarios;
    }
}
