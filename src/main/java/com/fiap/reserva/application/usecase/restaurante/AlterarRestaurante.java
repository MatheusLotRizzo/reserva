package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
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

        try {
            Restaurante encontrado = new BuscarRestaurante(repository).getRestaurantePor(restaurante.getCnpj());
            // A lógica para alterar o restaurante segue aqui, assumindo que o restaurante foi encontrado
            return repository.alterar(restaurante);
        } catch (EntidadeNaoEncontrada e) {
            // Lançar uma exceção específica para a operação de alteração
            throw new EntidadeNaoEncontrada("Restaurante não pode ser alterado, pois não foi encontrado");
        }
    }
}
