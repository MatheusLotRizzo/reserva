package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.vo.EmailVo;

import java.util.List;

public interface UsuarioRepository {

    List<Usuario> buscarTodos(Usuario usuario);
    Usuario buscar(Usuario usuario);
    Usuario buscarPorEmail(EmailVo email);
    void cadastrar(Usuario usuario);
    void alterar(Usuario usuario);
    void excluir(Usuario usuario);
}
