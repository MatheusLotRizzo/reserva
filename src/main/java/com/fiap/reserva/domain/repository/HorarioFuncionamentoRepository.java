package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.time.DayOfWeek;

public interface HorarioFuncionamentoRepository {
    boolean existeHorario(CnpjVo cnpj, DayOfWeek diaDaSemana);
    void cadastrar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento);
    void alterar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento);

}
