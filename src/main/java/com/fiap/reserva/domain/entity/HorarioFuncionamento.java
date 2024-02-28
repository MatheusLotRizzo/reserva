package com.fiap.reserva.domain.entity;

import java.time.LocalDateTime;

public class HorarioFuncionamento {
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;

    public HorarioFuncionamento(LocalDateTime horarioInicial, LocalDateTime horarioFinal) {
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
    }
}
