package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public class BuscarRestaurante {

    private final RestauranteRepository repository;
    public BuscarRestaurante(RestauranteRepository restauranteRepository) {
        this.repository =  restauranteRepository;
    }

    /**
     * Busca um restaurante por cnpj no repositorio
     * @param cnpjRestaurante
     * @return
     * @throws EntidadeNaoEncontrada caso não encontre o restaurante
     */
    public Restaurante getRestaurantePor(CnpjVo cnpjRestaurante) throws BusinessException {
        Restaurante restaurante = repository.buscarPorCnpj(cnpjRestaurante);
        if (restaurante == null) {
            throw new EntidadeNaoEncontrada("Restaurante não encontrado");
        }
        return restaurante;
    }

    /**
     * Busca um restaurante pelo nome no repositorio
     * @param cnpjRestaurante
     * @return
     * @throws EntidadeNaoEncontrada caso não encontre o restaurante
     */
    public Restaurante getRestaurantePorNome(String nome) throws BusinessException {
        Restaurante restaurante = repository.buscarPorNome(nome);
        if (restaurante == null) {
            throw new EntidadeNaoEncontrada("Restaurante não encontrado para o nome: " + nome);
        }
        return restaurante;
    }

    public List<Restaurante> getRestaurantePorTipoCozinha(TipoCozinha tipoCozinha) throws BusinessException {
        return repository.buscarPorTipoCozinha(tipoCozinha);
    }

    public List<Restaurante> getRestaurantePorLocalizacao(EnderecoVo enderecoVo) throws BusinessException {
        return repository.buscarPorLocalizacao(enderecoVo);
    }
}
