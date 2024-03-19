package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.avaliacao.BuscarAvaliacaoPorRestaurante;
import com.fiap.reserva.application.usecase.avaliacao.DeixarAvaliacao;
import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.util.List;

public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, RestauranteService restauranteService,UsuarioService usuarioService) {
        this.repository = avaliacaoRepository;
        this.restauranteService = restauranteService;
        this.usuarioService = usuarioService;
    }

    public Avaliacao avaliar(final Avaliacao avaliacao) throws BusinessException{
        if(avaliacao == null) {
            throw new BusinessException("Avaliacao é obrigatorio");
        }
        final Usuario usuario = usuarioService.getBuscarPor(avaliacao.getUsuario().getEmail());
        final Restaurante restaurante = restauranteService.getBuscarPor(avaliacao.getRestaurante().getCnpj());
        final Avaliacao avaliacaoValidada = new Avaliacao(usuario,restaurante,avaliacao.getPontuacao(),avaliacao.getComentario());
        return new DeixarAvaliacao(repository).executar(avaliacaoValidada);
    }

    public List<Avaliacao> getBuscarTodasAvaliacoesRestaurantePeloCNPJ(final CnpjVo cnpj) throws BusinessException {
        final Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
        return new BuscarAvaliacaoPorRestaurante(repository).executar(restaurante);
    }
}
