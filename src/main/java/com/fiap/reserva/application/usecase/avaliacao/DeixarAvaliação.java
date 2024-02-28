package com.fiap.reserva.application.usecase.avaliacao;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class DeixarAvaliação {
    private final AvaliacaoRepository repository;

    public DeixarAvaliação(AvaliacaoRepository avaliacaoRepository) {
        this.repository = avaliacaoRepository;
    }

    public void executar(Avaliacao avaliacao){
        this.repository.avaliar(avaliacao);
    }
}
