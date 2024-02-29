package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

public interface HorarioFuncionamentoRepository {
    void cadastrar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento);
    void alterar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento);

}
