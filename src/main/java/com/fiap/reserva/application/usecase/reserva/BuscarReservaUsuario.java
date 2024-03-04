package com.fiap.reserva.application.usecase.reserva;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

public class BuscarReservaUsuario {

    final ReservaRepository repository;

    public BuscarReservaUsuario(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> executar(Usuario usuario) throws BusinessException{
        if(usuario == null){
            throw new BusinessException("Usuario é obrigatorio para realizar a busca!!");
        }
        
        return repository.buscarTodasPor(usuario);
    }
}
