package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.BuscarRestaurante;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class RestauranteService {
    private RestauranteRepository repository;


    public Restaurante getBuscarPor(final CnpjVo cnpj) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePor(cnpj);
    }
}
