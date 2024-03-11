package com.fiap.spring.Controller.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.vo.EnderecoVo;

public record RestauranteDto(
        String cnpj,
        String nome,
        int capacidade,
        TipoCozinha tipoCozinha,
        List<HorarioFuncionamentoDto> horariosFuncionamento,
        EnderecoDto endereco
) {
	public Restaurante toEntity() {
		final List<HorarioFuncionamento> hrf = this.horariosFuncionamento.stream()
			.map(HorarioFuncionamentoDto::toEntity)
			.collect(Collectors.toList());
		
		final EnderecoVo enderecoEntity = endereco == null ? null: endereco.toEntity();
		return new Restaurante(cnpj, nome, enderecoEntity, hrf, capacidade, tipoCozinha);
	}
}
