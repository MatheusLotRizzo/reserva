package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;

import java.util.List;

public interface AvaliacaoRepository {
    List<Avaliacao> buscarTodos(Restaurante restaurante);
    Avaliacao avaliar(Avaliacao avaliacao);
}
