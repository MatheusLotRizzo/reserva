package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class BuscarRestaurante {

    private final RestauranteRepository repository;
    public BuscarRestaurante(RestauranteRepository restauranteRepository) {
        this.repository =  restauranteRepository;
    }

    public Restaurante getRestaurantePor(String cnpjRestaurante) throws BusinessException  {
        return repository.buscarPorCnpj(new CnpjVo(cnpjRestaurante));
    }

    public Restaurante getRestaurantePorNome(String nome) throws BusinessException {
        return repository.buscarPorNome(nome);
    }

    public Restaurante getRestaurantePorTipoCozinha(TipoCozinha tipoCozinha) {
        return repository.buscarPorTipoCozinha(tipoCozinha);
    }

}
