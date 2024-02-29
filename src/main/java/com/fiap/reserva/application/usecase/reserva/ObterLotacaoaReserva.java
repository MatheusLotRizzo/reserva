package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class ObterLotacaoaReserva {
    private final ReservaRepository repository;
    public ObterLotacaoaReserva(ReservaRepository repository) {
        this.repository = repository;
    }

    public Integer executar(Reserva reserva) throws BusinessException {
        if(reserva == null){
            throw new BusinessException("Reserva é obrigatorio");
        }

        if(reserva.getRestaurante() == null){
            throw new BusinessException("Restaurante é obrigatorio");
        }

        return repository.obterLotacaoaReserva(reserva);
    }
}
