package com.fiap.reserva.application.controller;

import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.spring.Controller.Dto.RestauranteDto;

import java.util.List;
import java.util.stream.Collectors;

public class RestauranteControllerApplication {

    private RestauranteService service;

    public RestauranteDto cadastrar(final RestauranteDto restauranteDto) throws BusinessException{
        return construirRestauranteDto(service.cadastrar(construirRestaurante(restauranteDto)));
    }

    public RestauranteDto alterar(final RestauranteDto restauranteDto) throws BusinessException {
        return construirRestauranteDto(service.alterar(construirRestaurante(restauranteDto)));
    }

    public void excluir(final String cnpj) throws BusinessException{
        service.excluir(new CnpjVo(cnpj));
    }

    public RestauranteDto getBuscarPor(final String cnpj) throws BusinessException{
        return construirRestauranteDto(service.getBuscarPor(new CnpjVo(cnpj)));
    }

    public RestauranteDto getBuscarPorNome(final String nome) throws BusinessException{
        return construirRestauranteDto(service.getBuscarPorNome(nome));
    }

    public List<RestauranteDto> getBuscarPorTipoCozinha(final String tipoCozinha) throws BusinessException{

        List<Restaurante> restaurantes = service.getBuscarPorTipoCozinha(TipoCozinha.valueOf(tipoCozinha));

        return restaurantes.stream()
                .map(this::construirRestauranteDto)
                .collect(Collectors.toList());
    }

    public List<RestauranteDto> getBuscarPorLocalizacao(final RestauranteDto restauranteDto) throws BusinessException{

        List<Restaurante> restaurantes = service.getBuscarPorLocalizacao(construirRestaurante(restauranteDto).getEndereco());

        return restaurantes.stream()
                .map(this::construirRestauranteDto)
                .collect(Collectors.toList());
    }

    private Restaurante construirRestaurante(final RestauranteDto restauranteDto) {
        EnderecoVo enderecoVo = new EnderecoVo(restauranteDto.endereco().cep(), restauranteDto.endereco().logradouro(), restauranteDto.endereco().numero(), restauranteDto.endereco().complemento(), restauranteDto.endereco().bairro(), restauranteDto.endereco().cidade(), restauranteDto.endereco().estado());
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(restauranteDto.horarioFuncionamento().horarioInicial(), restauranteDto.horarioFuncionamento().horarioFinal());

        return new Restaurante(new CnpjVo(restauranteDto.cnpj()),
                restauranteDto.nome(),
                enderecoVo,
                horarioFuncionamento,
                restauranteDto.capacidade(),
                TipoCozinha.valueOf(restauranteDto.tipoCozinha()));
    }

    private RestauranteDto construirRestauranteDto(final Restaurante restaurante){
        return restaurante.toDto();
    }
}
