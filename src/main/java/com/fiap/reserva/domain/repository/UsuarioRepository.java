package com.fiap.reserva.domain.repository;

import java.util.List;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.EmailVo;

public interface UsuarioRepository{

    List<Usuario> buscarTodos(Usuario usuario) throws BusinessException;
    Usuario buscarPor(Usuario usuario)throws BusinessException;
    Usuario cadastrar(Usuario usuario)throws BusinessException;
    Usuario alterar(Usuario usuario)throws BusinessException;
    void excluir(EmailVo email)throws BusinessException;
}
