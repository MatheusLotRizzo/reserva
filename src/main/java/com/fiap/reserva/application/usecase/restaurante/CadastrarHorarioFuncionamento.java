package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class CadastrarHorarioFuncionamento {
    private final HorarioFuncionamentoRepository repository;
    public CadastrarHorarioFuncionamento(HorarioFuncionamentoRepository horarioFuncionamentoRepository) {
        this.repository = horarioFuncionamentoRepository;
    }

    public void executar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) throws BusinessException {
        if(horarioFuncionamento == null){
            throw new BusinessException("Horario Funcionamento é obrigatorio");
        }

        repository.cadastrar(cnpj,horarioFuncionamento);
    }
}
