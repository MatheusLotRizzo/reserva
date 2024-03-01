package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

public class ExcluirRestaurante {
    private final RestauranteRepository repository;

    public ExcluirRestaurante(RestauranteRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public void executar(final CnpjVo cnpj) throws BusinessException {
        if(new BuscarRestaurante(repository).getRestaurantePor(cnpj) == null){
            throw new BusinessException("Restaurante não pode ser excluido, pois nao foi encontrada");
        }
        repository.excluir(cnpj);
    }
}
