package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class ExcluirReservaRestaurante {

    private final ReservaRepository repository;

    public ExcluirReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

    public void executar(final Reserva reserva) throws BusinessException{
        if(new BuscarReserva(repository).getReserva(reserva) == null){
            throw new BusinessException("Reserva não pode ser excluida, pois nao foi encontrada");
        }
        repository.excluir(reserva);
    }
}
