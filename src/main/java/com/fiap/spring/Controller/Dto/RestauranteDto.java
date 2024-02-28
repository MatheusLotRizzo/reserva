package com.fiap.spring.Controller.Dto;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.time.LocalDateTime;

public record RestauranteDto(
        String cnpj,
        String nome,
        int capacidade,
        String tipoCozinha,
        String horarioAbertura,
        String horarioEncerramento,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
) {
}
