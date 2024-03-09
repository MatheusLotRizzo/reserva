package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.AlterarHorarioFuncionamento;
import com.fiap.reserva.application.usecase.restaurante.CadastrarHorarioFuncionamento;
import com.fiap.reserva.application.usecase.restaurante.ObterHorarioFuncionamento;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class HorarioSuncionamentoService {
    private final HorarioFuncionamentoRepository repository;
   // RESTURANTE SERVICE NAO DEVERIA SER CHAMADO DENTRO DE HORARIO
   // private final RestauranteService restauranteService;


    public HorarioSuncionamentoService(HorarioFuncionamentoRepository repository) {
		this.repository = repository;
		//this.restauranteService = restauranteService;
	}

	public void cadastrar(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
        new CadastrarHorarioFuncionamento(repository).executar(cnpj,horarioFuncionamento);
    }

    public void alterar(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException  {
    	//ISSO NAO PODE OCORRER AQUI O RESTAURANTE TEM DE SER PASSADO DE FORA PRONTO NAO ~E OBRIGAÇAO
    	//DESSE SERVICE VALIDAR , INCLUSIVE ACREDITO QUE RESTAURANTE PODERIA TER TODA ESSA REGRA
    	//        Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
		//      	  if (restaurante == null){
		//            throw new BusinessException("Restaurante não existe");
		//        }

        new AlterarHorarioFuncionamento(repository).executar(cnpj,horarioFuncionamento);
    }

    public HorarioFuncionamento getObter(final CnpjVo cnpj, final HorarioFuncionamento horarioFuncionamento) throws BusinessException{
        return new ObterHorarioFuncionamento(repository).getObter(cnpj,horarioFuncionamento);
    }
}
