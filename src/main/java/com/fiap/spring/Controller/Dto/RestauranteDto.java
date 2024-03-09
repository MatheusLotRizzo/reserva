package com.fiap.spring.Controller.Dto;

import com.fiap.reserva.domain.entity.TipoCozinha;

public record RestauranteDto(
        String cnpj,
        String nome,
        int capacidade,
        TipoCozinha tipoCozinha,
        HorarioFuncionamentoDto horarioFuncionamento,
        EnderecoDto endereco
) {
}
