package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class AlterarHorarioFuncionamento {
    private final HorarioFuncionamentoRepository repository;
    public AlterarHorarioFuncionamento(HorarioFuncionamentoRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public void executar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento ) throws BusinessException {
        if(horarioFuncionamento == null){
            throw new BusinessException("Horario Funcionamento é obrigatorio");
        }

        repository.alterar(cnpj,horarioFuncionamento);
    }
}
