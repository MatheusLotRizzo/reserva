package com.fiap.reserva.application.controller;

import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.UsuarioDto;

public class UsuarioControllerApplication {

    private UsuarioService service;

    public UsuarioDto cadastrar(final UsuarioDto usuarioDto){
        return construirUsuarioDto( service.cadastrar(construirUsuario(usuarioDto)) );
    }

    public UsuarioDto alterar(final UsuarioDto usuarioDto) throws BusinessException {
        return construirUsuarioDto( service.alterar(construirUsuario(usuarioDto)) );
    }

    public void excluir(final String email){
        service.excluir(new EmailVo(email));
    }

    public UsuarioDto getBuscarPor(final UsuarioDto usuarioDto) throws BusinessException{
        return construirUsuarioDto(service.getBuscarPor(construirUsuario(usuarioDto)));
    }

    public UsuarioDto getBuscarPorEmail(final String email) throws BusinessException{
        return construirUsuarioDto(service.getBuscarPorEmail(new EmailVo(email)));
    }

    private Usuario construirUsuario(final UsuarioDto usuarioDto) {
        return new Usuario(usuarioDto.nome(), usuarioDto.email(), usuarioDto.celular());
    }

    private UsuarioDto construirUsuarioDto(final Usuario usuario){
        return usuario.toDto();
    }
}
