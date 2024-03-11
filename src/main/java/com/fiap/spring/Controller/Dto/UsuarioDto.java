package com.fiap.spring.Controller.Dto;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;

public record UsuarioDto(
        String nome,
        String email,
        String celular
) {
	public Usuario toEntity() throws BusinessException {
		return new Usuario(nome, email, celular);
	}
}
