package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.AlterarEndereco;
import com.fiap.reserva.application.usecase.restaurante.CadastrarEndereco;
import com.fiap.reserva.application.usecase.restaurante.ObterEndereco;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

public class EnderecoService {

    private final EnderecoRepository repository;
    //NAO DEVERIA ESTAR AQUI 
   // private final RestauranteService restauranteService;


    public EnderecoService(EnderecoRepository repository) {
		this.repository = repository;
		//this.restauranteService = restauranteService;
	}

	public void cadastrar(final CnpjVo cnpj, final EnderecoVo enderecoVo) throws BusinessException  {
        new CadastrarEndereco(repository).executar(cnpj,enderecoVo);
    }

    public void alterar(final CnpjVo cnpj, final EnderecoVo enderecoVo) throws BusinessException  {
        //NAO OBRIGAÇAO DESSA SERVICE VALIDAR RESTAURANTE
    	//Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
        //if (restaurante == null){
        //    throw new BusinessException("Restaurante não existe");
        //}
        new AlterarEndereco(repository).executar(cnpj,enderecoVo);
    }

    public EnderecoVo getObter(final CnpjVo cnpj, final EnderecoVo enderecoVo) throws BusinessException{
        return new ObterEndereco(repository).getObter(cnpj,enderecoVo);
    }
}
