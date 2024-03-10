package com.fiap.reserva.domain.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class HorarioFuncionamento {
	private final DayOfWeek diaDaSemana;
    private final LocalDateTime horarioInicial;
    private final LocalDateTime horarioFinal;

    public HorarioFuncionamento(DayOfWeek diaDaSemana, LocalDateTime horarioInicial, LocalDateTime horarioFinal) {
		this.diaDaSemana = diaDaSemana;
		this.horarioInicial = horarioInicial;
		this.horarioFinal = horarioFinal;
	}

	public LocalDateTime getHorarioInicial() {
        return horarioInicial;
    }

    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }
    
    public DayOfWeek getDiaDaSemana() {
		return diaDaSemana;
	}
}
