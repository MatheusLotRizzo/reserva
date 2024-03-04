package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;


public class CadastrarReserva {

    private final ReservaRepository repository;

    public CadastrarReserva(ReservaRepository repository) {
        this.repository = repository;
    }

    public Reserva executar(Reserva reserva) throws BusinessException{
        if(reserva == null) {
        	throw new BusinessException("Preencha uma reserva para ser salva");
        }
    	reserva.reservar();
    	
        return this.repository.criar(reserva);
    }
}
