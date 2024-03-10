package com.fiap.spring.Controller.Dto;

import com.fiap.reserva.domain.vo.EnderecoVo;

public record EnderecoDto (
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado
) {
	public EnderecoVo toEntity() {
		return new EnderecoVo(cep, logradouro, numero, complemento, bairro, cidade, estado);
	}
}
