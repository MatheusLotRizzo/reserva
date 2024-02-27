package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository {

    List<Reserva> buscarTodasPor(Restaurante restaurante);
    List<Reserva> buscarTodasPor(Usuario usuario);
    List<Reserva> buscarTodasPor(Reserva reserva);
    Reserva buscar(Reserva reserva);
    void cadastrar(Reserva reserva);
    void alterar(Reserva reserva);
    void excluir(EmailVo email, CnpjVo cnpjVo, LocalDateTime dataHora);
}
