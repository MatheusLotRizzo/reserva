package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;

public class AlterarRestaurante {

    private final RestauranteRepository repository;

    public AlterarRestaurante(RestauranteRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public Restaurante executar(Restaurante restaurante) throws BusinessException {
        if (restaurante == null) {
            throw new BusinessException("Restaurante é obrigatorio");
        }
        return repository.alterar(restaurante);
    }
}
