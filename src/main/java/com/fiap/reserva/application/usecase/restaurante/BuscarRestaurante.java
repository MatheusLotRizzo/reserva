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

    public Restaurante getRestaurantePor(CnpjVo cnpjRestaurante) throws BusinessException {
        Restaurante restaurante = repository.buscarPorCnpj(cnpjRestaurante);
        if (restaurante == null) {
            throw new BusinessException("Restaurante não encontrado");
        }
        return restaurante;
    }

    public Restaurante getRestaurantePorNome(String nome) throws BusinessException {
        Restaurante restaurante = repository.buscarPorNome(nome);
        if (restaurante == null) {
            throw new BusinessException("Restaurante não encontrado para o nome: " + nome);
        }
        return restaurante;
    }

    public List<Restaurante> getRestaurantePorTipoCozinha(TipoCozinha tipoCozinha) {
        return repository.buscarPorTipoCozinha(tipoCozinha);
    }

    public List<Restaurante> getRestaurantePorLocalizacao(EnderecoVo enderecoVo) {
        return repository.buscarPorLocalizacao(enderecoVo);
    }

}
