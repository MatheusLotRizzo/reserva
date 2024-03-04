package com.fiap.reserva.application.usecase.reserva;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReservaRestaurante {

    final ReservaRepository repository;

    public BuscarReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> executar(Restaurante restaurante) throws BusinessException{
        if(restaurante == null){
            throw new BusinessException("Restaurante é obrigatorio para realizar a busca!!");
        }
        
        return repository.buscarTodasPor(restaurante);
    }

}
