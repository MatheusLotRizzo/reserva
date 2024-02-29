package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class CadastrarReserva {

    private final ReservaRepository repository;

    public CadastrarReserva(ReservaRepository repository) {
        this.repository = repository;
    }

    public Reserva executar(Reserva reserva){
        reserva.reservar();
        return this.repository.criar(reserva);
    }
}
