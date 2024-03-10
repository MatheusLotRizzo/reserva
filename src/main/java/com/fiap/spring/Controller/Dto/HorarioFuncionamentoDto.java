package com.fiap.spring.Controller.Dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;

public record HorarioFuncionamentoDto(
	DayOfWeek diaDaSemana,
    LocalDateTime horarioInicial,
    LocalDateTime horarioFinal
) {
	public HorarioFuncionamento toEntity(){
		return new HorarioFuncionamento(diaDaSemana, horarioInicial, horarioFinal);
	}
}
