package com.fiap.spring.Controller.Dto;

import java.time.LocalDateTime;

public record HorarioFuncionamentoDto(
    LocalDateTime horarioInicial,
    LocalDateTime horarioFinal
) {
}
