package com.fiap.spring.Controller.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;

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
		
		return new Restaurante(cnpj, nome, endereco.toEntity(), hrf, capacidade, tipoCozinha);
	}
}
