package com.fiap.reserva.domain.entity;

import com.fiap.spring.Controller.Dto.HorarioFuncionamentoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class HorarioFuncionamento {
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;

    public HorarioFuncionamento(LocalDateTime horarioInicial, LocalDateTime horarioFinal) {
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
    }

    public HorarioFuncionamentoDto toDto(){
        return new HorarioFuncionamentoDto(
                this.horarioInicial,
                this.horarioFinal
        );
    }
}
