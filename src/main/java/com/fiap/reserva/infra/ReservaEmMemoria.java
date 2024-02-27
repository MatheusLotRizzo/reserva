package com.fiap.reserva.infra;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

import java.time.LocalDateTime;
import java.util.List;

public class ReservaEmMemoria implements ReservaRepository {

    @Override
    public List<Reserva> buscarTodasPor(Restaurante restaurante) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodasPor'");
    }

    @Override
    public List<Reserva> buscarTodasPor(Usuario usuario) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodasPor'");
    }

    @Override
    public List<Reserva> buscarTodasPor(Reserva reserva) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodasPor'");
    }

    @Override
    public Reserva buscar(Reserva reserva) {
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }

    @Override
    public void cadastrar(Reserva reserva) {
        throw new UnsupportedOperationException("Unimplemented method 'cadastrar'");
    }

    @Override
    public void alterar(Reserva reserva) {
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

    @Override
    public void excluir(EmailVo email, CnpjVo cnpjVo, LocalDateTime dataHora) {
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }
   
    
}
