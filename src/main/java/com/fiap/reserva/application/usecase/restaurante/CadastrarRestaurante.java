package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.repository.RestauranteRepository;

public class CadastrarRestaurante {

    private final RestauranteRepository repository;
    public CadastrarRestaurante(RestauranteRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public void executar(Restaurante restaurante){
        this.repository.cadastrar(restaurante);

    }
}
