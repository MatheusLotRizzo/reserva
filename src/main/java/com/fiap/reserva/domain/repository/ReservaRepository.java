package com.fiap.reserva.domain.repository;

import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;

public interface ReservaRepository {
    List<Reserva> buscarTodasPor(Restaurante restaurante);
    List<Reserva> buscarTodasPor(Usuario usuario);
    List<Reserva> buscarTodasPor(Reserva reserva);
    Reserva buscar(Reserva reserva);
    Reserva criar(Reserva reserva);
    Reserva alterar(Reserva reserva);
    void excluir(Reserva reserva);
   
}
