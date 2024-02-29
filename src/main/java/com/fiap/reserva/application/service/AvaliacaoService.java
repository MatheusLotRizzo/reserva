package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.avaliacao.BuscarAvaliacaoPorRestaurante;
import com.fiap.reserva.application.usecase.avaliacao.DeixarAvaliacao;
import com.fiap.reserva.application.usecase.restaurante.BuscarRestaurante;
import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.util.List;

public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final RestauranteRepository restauranteRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository) {
        this.repository = avaliacaoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Avaliacao avaliar(final Avaliacao avaliacao){
        return new DeixarAvaliacao(repository).executar(avaliacao);
    }

    public List<Avaliacao> getBuscarTodos(final CnpjVo cnpj) throws BusinessException {
        final Restaurante restaurante = new BuscarRestaurante(restauranteRepository).getRestaurantePor(cnpj);
        return new BuscarAvaliacaoPorRestaurante(repository).todasAvaliacoes(restaurante);
    }
}
