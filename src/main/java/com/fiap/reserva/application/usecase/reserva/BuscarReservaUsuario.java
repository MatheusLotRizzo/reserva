package com.fiap.reserva.application.usecase.reserva;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReservaUsuario {

    private static final String USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA = "Usuario é obrigatorio para realizar a busca!!";
    final ReservaRepository repository;

    public BuscarReservaUsuario(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> todasReservasPor(Usuario usuario) throws BusinessException{
        if(usuario == null){
            throw new BusinessException(USUARIO_E_OBRIGATORIO_PARA_REALIZAR_A_BUSCA);
        }
        
        return repository.buscarTodasPor(usuario);
    }
}
