package com.fiap.spring.Controller.Dto;

public record RestauranteDto(
        String cnpj,
        String nome,
        int capacidade,
        String tipoCozinha,
        HorarioFuncionamentoDto horarioFuncionamento,
        EnderecoDto endereco
) {
}
