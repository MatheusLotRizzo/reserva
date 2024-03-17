package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;

public class AlterarRestaurante {

    private final RestauranteRepository repository;
    private final BuscarRestaurante buscarRestaurante;

    public AlterarRestaurante(RestauranteRepository restauranteRepository, BuscarRestaurante buscarRestaurante) {
        this.repository = restauranteRepository;
        this.buscarRestaurante = buscarRestaurante;
    }

    public Restaurante executar(Restaurante restaurante) throws BusinessException {
        if (restaurante == null) {
            throw new BusinessException("Restaurante é obrigatorio");
        }

        Restaurante encontrado = buscarRestaurante.getRestaurantePor(restaurante.getCnpj());
        if (encontrado == null) {
            throw new BusinessException("Restaurante não pode ser alterado, pois não foi encontrado");
        }

        return repository.alterar(restaurante);
    }
}
