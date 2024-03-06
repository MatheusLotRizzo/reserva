package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;

public class CadastrarRestaurante {

    private final RestauranteRepository repository;
    public CadastrarRestaurante(RestauranteRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public Restaurante executar(Restaurante restaurante) throws BusinessException{
        if(restaurante == null){
            throw new BusinessException("Restaurante é obrigatório");
        }
        return this.repository.cadastrar(restaurante);
    }
}
