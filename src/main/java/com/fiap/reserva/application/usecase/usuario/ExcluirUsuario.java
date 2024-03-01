package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

public class ExcluirUsuario {
    private final UsuarioRepository repository;

    public ExcluirUsuario(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public void executar(final EmailVo email) throws BusinessException {
        if(email == null){
            throw new BusinessException("Email do usuario é obrigatorio");
        }

        if(new BuscarUsuario(repository).getUsuarioPor(email) == null){
            throw new BusinessException("Usuário não pode ser excluido, pois nao foi encontrada");
        }
        repository.excluir(email);
    }
}
