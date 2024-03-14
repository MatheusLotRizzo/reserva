package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class ConcluirReservaRestaurante {

    private final ReservaRepository repository;

    public ConcluirReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

    public void executar(final Reserva reserva) throws BusinessException{
    	if(reserva == null){
            throw new BusinessException("Informe a reserva para ser concluida");
        }
    	
    	reserva.concluir();
        repository.alterar(reserva);
    }
}
