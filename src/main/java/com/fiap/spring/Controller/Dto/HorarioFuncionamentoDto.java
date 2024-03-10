package com.fiap.spring.Controller.Dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public record HorarioFuncionamentoDto(
	DayOfWeek diaDaSemana,
    LocalDateTime horarioInicial,
    LocalDateTime horarioFinal
) {
}
