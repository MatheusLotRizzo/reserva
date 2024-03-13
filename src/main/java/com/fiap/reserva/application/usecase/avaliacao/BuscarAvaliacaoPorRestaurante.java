package com.fiap.reserva.application.usecase.avaliacao;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import java.util.List;

public class BuscarAvaliacaoPorRestaurante {
    final AvaliacaoRepository repository;

    public BuscarAvaliacaoPorRestaurante(AvaliacaoRepository avaliacaoRepository) {
        this.repository = avaliacaoRepository;
    }

    public List<Avaliacao> executar(Restaurante restaurante) throws BusinessException {
        if(restaurante == null){
            throw new BusinessException("Restaurante não foi encontrado!");
        }
        return repository.buscarTodasPor(restaurante);
    }
}
