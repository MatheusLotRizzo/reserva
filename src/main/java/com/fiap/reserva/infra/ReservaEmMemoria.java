package com.fiap.reserva.infra;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.repository.ReservaRepository;

import java.util.ArrayList;
import java.util.List;

public class ReservaEmMemoria implements ReservaRepository {

    private List<Reserva> reservaList = new ArrayList<>();
    @Override
    public List<Reserva> buscarTodos(Reserva reserva) {
        return this.reservaList;
    }

    @Override
    public Reserva buscar(Reserva reserva) {
        return this.reservaList.get(0);
    }

    @Override
    public void cadastrar(Reserva reserva) {
        this.reservaList.add(reserva);
    }

    @Override
    public void alterar(Reserva reserva) {

    }

    @Override
    public void excluir(Reserva reserva) {

    }
}
