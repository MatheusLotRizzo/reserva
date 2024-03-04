package com.fiap.reserva.domain.repository;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;

public interface ReservaRepository {
    List<Reserva> buscarTodasPor(Restaurante restaurante) throws BusinessException;
    List<Reserva> buscarTodasPor(Usuario usuario) throws BusinessException;
    List<Reserva> buscarTodasPor(Reserva reserva) throws BusinessException;
    Reserva buscarPor(Reserva reserva) throws BusinessException;
    Reserva criar(Reserva reserva) throws BusinessException;
    Reserva alterar(Reserva reserva) throws BusinessException;
    void excluir(Reserva reserva);
}
