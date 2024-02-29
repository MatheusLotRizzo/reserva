package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.AlterarHorarioFuncionamento;
import com.fiap.reserva.application.usecase.restaurante.CadastrarHorarioFuncionamento;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class HorarioSuncionamentoService {
    private HorarioFuncionamentoRepository repository;
    private RestauranteService restauranteService;

    public HorarioSuncionamentoService(HorarioFuncionamentoRepository repository) throws BusinessException {
        this.repository = repository;
    }

    public void cadastrar(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
        Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
        if (restaurante == null){
            throw new BusinessException("Restaurante não existe");
        }
        new CadastrarHorarioFuncionamento(repository).executar(cnpj,horarioFuncionamento);
    }

    public void alterar(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
        Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
        if (restaurante == null){
            throw new BusinessException("Restaurante não existe");
        }
        new AlterarHorarioFuncionamento(repository).executar(cnpj,horarioFuncionamento);
    }
}
