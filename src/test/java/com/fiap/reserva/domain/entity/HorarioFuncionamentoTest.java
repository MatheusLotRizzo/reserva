package com.fiap.reserva.domain.entity;

import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioFuncionamentoTest {

    @Test
    void deveCriarHorarioFuncionamentoCorretamente() {
        DayOfWeek diaDaSemana = DayOfWeek.MONDAY;
        LocalDateTime horarioInicial = LocalDateTime.of(2023, 3, 20, 9, 0);
        LocalDateTime horarioFinal = LocalDateTime.of(2023, 3, 20, 18, 0);

        HorarioFuncionamento horario = new HorarioFuncionamento(diaDaSemana, horarioInicial, horarioFinal);

        assertNotNull(horario);
        assertEquals(diaDaSemana, horario.getDiaDaSemana());
        assertEquals(horarioInicial, horario.getHorarioInicial());
        assertEquals(horarioFinal, horario.getHorarioFinal());
    }

    @Test
    void devePermitirCriacaoParaTodosOsDiasDaSemana() {
        for (DayOfWeek dia : DayOfWeek.values()) {
            LocalDateTime horarioInicial = LocalDateTime.of(2023, 3, 20, 9, 0);
            LocalDateTime horarioFinal = LocalDateTime.of(2023, 3, 20, 18, 0);
            HorarioFuncionamento horario = new HorarioFuncionamento(dia, horarioInicial, horarioFinal);

            assertEquals(dia, horario.getDiaDaSemana());
        }
    }
}

