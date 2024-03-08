package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;

public class ExcluirUsuario {
    private final UsuarioRepository repository;

    public ExcluirUsuario(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public void executar(final EmailVo emailVo) throws BusinessException {
        if(emailVo == null){
            throw new BusinessException("Email do usuario é obrigatorio");
        }
        repository.excluir(emailVo);
    }
}