package com.fiap.reserva.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.spring.Controller.Dto.EnderecoDto;
import com.fiap.spring.Controller.Dto.HorarioFuncionamentoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;

public class RestauranteControllerApplication {

    private final RestauranteService service;

    public RestauranteControllerApplication(RestauranteService service) {
		this.service = service;
	}

	public RestauranteDto cadastrar(final RestauranteDto restauranteDto) throws BusinessException{
        return toDto(service.cadastrar(restauranteDto.toEntity()));
    }

    public RestauranteDto alterar(final RestauranteDto restauranteDto) throws BusinessException {
        return toDto(service.alterar(restauranteDto.toEntity()));
    }

    public void excluir(final String cnpj) throws BusinessException{
        service.excluir(new CnpjVo(cnpj));
    }

    public RestauranteDto getBuscarPor(final String cnpj) throws BusinessException{
        return toDto(service.getBuscarPor(new CnpjVo(cnpj)));
    }

    public RestauranteDto getBuscarPorNome(final String nome) throws BusinessException{
        return toDto(service.getBuscarPorNome(nome));
    }

    public List<RestauranteDto> getBuscarPorTipoCozinha(final String tipoCozinha) throws BusinessException{

        List<Restaurante> restaurantes = service.getBuscarPorTipoCozinha(TipoCozinha.valueOf(tipoCozinha));

        return restaurantes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<RestauranteDto> getBuscarPorLocalizacao(final RestauranteDto restauranteDto) throws BusinessException{
        final List<Restaurante> restaurantes = service.getBuscarPorLocalizacao(restauranteDto.toEntity().getEndereco());

        return restaurantes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    
	public RestauranteDto toDto(Restaurante restaurante){
	  final List<HorarioFuncionamentoDto> horarioFuncionamentoDtos = restaurante.getHorarioFuncionamento()
	  		.stream().map(this::toDto)
	  		.collect(Collectors.toList());
	
	  return new RestauranteDto(
		  restaurante.getCnpjString(),
		  restaurante.getNome(),
		  restaurante.getCapacidadeMesas(),
		  restaurante.getTipoCozinha(),
          horarioFuncionamentoDtos,
          toDto(restaurante.getEndereco())
	  );
	}
	
	public  HorarioFuncionamentoDto toDto(final HorarioFuncionamento horarioFuncionamento){
        return new HorarioFuncionamentoDto(
    		horarioFuncionamento.getDiaDaSemana(),
    		horarioFuncionamento.getHorarioInicial(),
    		horarioFuncionamento.getHorarioFinal()
        );
    }
	
	 public EnderecoDto toDto(final EnderecoVo enderecoVo){
        return new EnderecoDto(
    		enderecoVo.getCep(),
    		enderecoVo.getLogradouro(),
    		enderecoVo.getNumero(),
    		enderecoVo.getComplemento(),
    		enderecoVo.getBairro(),
    		enderecoVo.getCidade(),
    		enderecoVo.getEstado()
        );
    }
}
