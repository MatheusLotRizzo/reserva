package com.fiap.reserva.application.usecase.reserva;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class AlterarReservaRestaurante {
     private final ReservaRepository repository;

    public AlterarReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

     public Reserva executar(Reserva reserva) throws BusinessException{
        if(reserva == null){
            throw new BusinessException("Informe a reserva para ser alterada");
        }
        
        return repository.alterar(reserva);
     }
}
