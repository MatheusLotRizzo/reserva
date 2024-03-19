package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.EmailVo;

public interface UsuarioRepository{

    Usuario buscarPor(EmailVo email)throws BusinessException;
    Usuario cadastrar(Usuario usuario)throws BusinessException;
    Usuario alterar(Usuario usuario)throws BusinessException;
    void excluir(EmailVo email)throws BusinessException;
}
