package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;

import java.util.List;

public interface ReservaRepository {

    List<Reserva> buscarTodos(Reserva reserva);
    Reserva buscar(Reserva reserva);
    void cadastrar(Reserva reserva);
    void alterar(Reserva reserva);
    void excluir(Reserva reserva);
}
