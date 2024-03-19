package com.fiap.reserva.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;

public interface ReservaRepository {
    List<Reserva> buscarTodasPor(Restaurante restaurante) throws BusinessException;
    List<Reserva> buscarTodasPor(Usuario usuario) throws BusinessException;
    Reserva buscarPor(UUID uuid) throws BusinessException;
    Reserva criar(Reserva reserva) throws BusinessException;
    Reserva alterar(Reserva reserva) throws BusinessException;
}
