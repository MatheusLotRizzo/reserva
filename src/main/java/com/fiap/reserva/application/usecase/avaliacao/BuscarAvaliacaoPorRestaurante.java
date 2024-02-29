package com.fiap.reserva.application.usecase.avaliacao;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import java.util.List;

public class BuscarAvaliacaoPorRestaurante {
    private static final String RESTAURANTE_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA = "Restaurante é obrigatorio para realizar a busca!!";
    final AvaliacaoRepository repository;

    public BuscarAvaliacaoPorRestaurante(AvaliacaoRepository avaliacaoRepository) {
        this.repository = avaliacaoRepository;
    }

    public List<Avaliacao> todasAvaliacoes(Restaurante restaurante) throws BusinessException {
        if(restaurante == null){
            throw new BusinessException(RESTAURANTE_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }

        return repository.buscarTodos(restaurante);
    }
}
