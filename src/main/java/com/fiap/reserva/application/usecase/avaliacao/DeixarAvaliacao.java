package com.fiap.reserva.application.usecase.avaliacao;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;

public class DeixarAvaliacao {
    private final AvaliacaoRepository repository;

    public DeixarAvaliacao(AvaliacaoRepository avaliacaoRepository) {
        this.repository = avaliacaoRepository;
    }

    public Avaliacao executar(Avaliacao avaliacao) throws BusinessException{
        if(avaliacao == null){
            throw new BusinessException("Avaliacao é obrigatorio");
        }

        return repository.avaliar(avaliacao);
    }
}
