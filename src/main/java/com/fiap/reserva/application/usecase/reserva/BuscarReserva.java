package com.fiap.reserva.application.usecase.reserva;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReserva {

    private static final String RESTAURANTE_USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA = "Restaurante e Usuario são obrigatorio para realizar a busca!!";
    final ReservaRepository repository;

    public BuscarReserva(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> todasReservasPor(Reserva reserva) throws BusinessException {
        if(reserva == null || reserva.getRestaurante() == null || reserva.getUsuario() == null){
            throw new BusinessException(RESTAURANTE_USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }
        
        return repository.buscarTodasPor(reserva);
    }

    public Reserva reservaPor(Reserva reserva) throws BusinessException {
        if(reserva == null || reserva.getRestaurante() == null || reserva.getUsuario() == null){
            throw new BusinessException(RESTAURANTE_USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }
        
        return repository.buscar(reserva);
    }    

}
