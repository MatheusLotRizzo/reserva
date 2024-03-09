package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;

import java.util.List;

public interface AvaliacaoRepository {
    List<Avaliacao> buscarTodasPor(Restaurante restaurante) throws BusinessException;
    Avaliacao avaliar(Avaliacao avaliacao) throws BusinessException;
}
