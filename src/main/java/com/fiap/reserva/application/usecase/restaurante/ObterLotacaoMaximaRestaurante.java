package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;

public class ObterLotacaoMaximaRestaurante {
    private final RestauranteRepository repository;
    public ObterLotacaoMaximaRestaurante(RestauranteRepository repository) {
        this.repository = repository;
    }

    public Integer executar(Restaurante restaurante) throws BusinessException {
        if(restaurante == null){
            throw new BusinessException("Restaurante é obrigatorio");
        }

        return repository.obterLotacaoMaximaRestaurante(restaurante);
    }
}
