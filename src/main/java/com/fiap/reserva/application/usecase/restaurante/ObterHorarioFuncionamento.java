package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class ObterHorarioFuncionamento {

    private final HorarioFuncionamentoRepository repository;
    public ObterHorarioFuncionamento(HorarioFuncionamentoRepository horarioFuncionamentoRepository) {
        this.repository =  horarioFuncionamentoRepository;
    }

    public HorarioFuncionamento getObter(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
        return repository.obter(cnpj,horarioFuncionamento);
    }

}
