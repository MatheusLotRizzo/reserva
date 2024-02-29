package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class ExcluirReservaRestaurante {

    private final ReservaRepository repository;

    public ExcluirReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

    public void executar(final Reserva reserva){
        repository.excluir(reserva);
    }
}
