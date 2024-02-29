package com.fiap.reserva.application.usecase.reserva;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReserva {

    private static final String RESTAURANTE_USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA = "Restaurante e Usuario são obrigatorio para realizar a busca!!";
    final ReservaRepository repository;

    public BuscarReserva(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> getTodasReserva(Reserva reserva) throws BusinessException {
        if(reserva == null || reserva.getRestaurante() == null || reserva.getUsuario() == null){
            throw new BusinessException(RESTAURANTE_USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }
        
        return repository.buscarTodasPor(reserva);
    }

    public List<Reserva> getTodasReservaPorUsuario(Usuario usuario) throws BusinessException {
        return repository.buscarTodasPor(usuario);
    }

    public List<Reserva> getTodasReservaPorRestaurante(Restaurante restaurante) throws BusinessException {
        return repository.buscarTodasPor(restaurante);
    }

    public Reserva getReserva(Reserva reserva) throws BusinessException {
        return repository.buscar(reserva);
    }

    public Reserva reservaPor(Reserva reserva) throws BusinessException {
        if(reserva == null || reserva.getRestaurante() == null || reserva.getUsuario() == null){
            throw new BusinessException(RESTAURANTE_USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }
        
        return repository.buscar(reserva);
    }    

}
