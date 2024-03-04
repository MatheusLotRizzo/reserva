package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.vo.EmailVo;

import java.util.List;

public interface UsuarioRepository {

    List<Usuario> buscarTodos(Usuario usuario);
    Usuario buscarPor(Usuario usuario);
    Usuario cadastrar(Usuario usuario);
    Usuario alterar(Usuario usuario);
    void excluir(EmailVo email);
}
