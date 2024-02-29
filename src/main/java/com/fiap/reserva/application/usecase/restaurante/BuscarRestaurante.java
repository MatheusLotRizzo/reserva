package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public class BuscarRestaurante {

    private final RestauranteRepository repository;
    public BuscarRestaurante(RestauranteRepository restauranteRepository) {
        this.repository =  restauranteRepository;
    }

    public Restaurante getRestaurantePor(CnpjVo cnpjRestaurante) throws BusinessException  {
        return repository.buscarPorCnpj(cnpjRestaurante);
    }

    public Restaurante getRestaurantePorNome(String nome) throws BusinessException {
        return repository.buscarPorNome(nome);
    }

    public List<Restaurante> getRestaurantePorTipoCozinha(TipoCozinha tipoCozinha) {
        return repository.buscarPorTipoCozinha(tipoCozinha);
    }

    public List<Restaurante> getRestaurantePorLocalizacao(EnderecoVo enderecoVo) {
        return repository.buscarPorLocalizacao(enderecoVo);
    }

}
