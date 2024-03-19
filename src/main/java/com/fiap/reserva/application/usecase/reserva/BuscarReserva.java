package com.fiap.reserva.application.usecase.reserva;

import java.util.UUID;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReserva {

    final ReservaRepository repository;

    public BuscarReserva(ReservaRepository repository) {
        this.repository = repository;
    }
    
    public Reserva executar(UUID codigo) throws BusinessException {
        if(codigo == null){
            throw new BusinessException("Número da reserva é obrigatório");
        }
        
        return repository.buscarPor(codigo);
    }

}
