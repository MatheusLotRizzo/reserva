package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.AlterarHorarioFuncionamento;
import com.fiap.reserva.application.usecase.restaurante.CadastrarHorarioFuncionamento;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class HorarioSuncionamentoService {
    private final HorarioFuncionamentoRepository repository;

    public HorarioSuncionamentoService(HorarioFuncionamentoRepository repository) {
		this.repository = repository;
	}

	public void cadastrar(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
        new CadastrarHorarioFuncionamento(repository).executar(cnpj,horarioFuncionamento);
    }

    public void alterar(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
        new AlterarHorarioFuncionamento(repository).executar(cnpj,horarioFuncionamento);
    }
}
