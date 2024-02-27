package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Avaliacao;
import java.util.List;

public interface AvaliacaoRepository {
    List<Avaliacao> buscarTodos(Avaliacao avaliacao);
    void cadastrar(Avaliacao avaliacao);
}
