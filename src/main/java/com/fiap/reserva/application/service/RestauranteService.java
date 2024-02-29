package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.AlterarRestaurante;
import com.fiap.reserva.application.usecase.restaurante.BuscarRestaurante;
import com.fiap.reserva.application.usecase.restaurante.CadastrarRestaurante;
import com.fiap.reserva.application.usecase.restaurante.ExcluirRestaurante;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.util.List;

public class RestauranteService {
    private RestauranteRepository repository;

    public Restaurante cadastrar(final Restaurante restaurante){
        return new CadastrarRestaurante(repository).executar(restaurante);
    }

    public Restaurante alterar(final Restaurante restaurante) throws BusinessException {
        return new AlterarRestaurante(repository).executar(restaurante);
    }

    public void excluir(final CnpjVo cnpj){
        new ExcluirRestaurante(repository).executar(cnpj);
    }

    public Restaurante getBuscarPorNome(final String nome) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorNome(nome);
    }

    public List<Restaurante> getBuscarPorTipoCozinha(final TipoCozinha tipoCozinha) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorTipoCozinha(tipoCozinha);
    }

    public Restaurante getBuscarPor(final CnpjVo cnpj) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePor(cnpj);
    }
}
