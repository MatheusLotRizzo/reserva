package com.fiap.reserva.application.usecase.reserva;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReservaRestaurante {

    private static final String RESTAURANTE_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA = "Restaurante é obrigatorio para realizar a busca!!";
    final ReservaRepository repository;

    public BuscarReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> todasReservasPor(Restaurante restaurante) throws BusinessException{
        if(restaurante == null){
            throw new BusinessException(RESTAURANTE_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }
        
        return repository.buscarTodasPor(restaurante);
    }

}
