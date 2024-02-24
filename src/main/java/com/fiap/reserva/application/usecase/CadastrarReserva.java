package com.fiap.reserva.application.usecase;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class CadastrarReserva {

    private final ReservaRepository reservaRepository;

    public CadastrarReserva(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void executar(Reserva reserva){
        reserva.reservar();
        this.reservaRepository.cadastrar(reserva);
    }
}
