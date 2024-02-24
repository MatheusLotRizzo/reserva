package com.fiap.reserva.adapter;

import com.fiap.reserva.application.controller.ReservaDto;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

import java.time.LocalDateTime;

public class ReservaAdapter {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaAdapter(RestauranteRepository restauranteRepository, UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Reserva toReserva(ReservaDto reservaDto){
        final Usuario usuario = this.usuarioRepository.buscarPorEmail(new EmailVo(reservaDto.emailUsuario()));
        final Restaurante restaurante = this.restauranteRepository.buscarPorCnpj(new CnpjVo(reservaDto.cnpjRestaurante()));

        return new Reserva(usuario,restaurante,reservaDto.dataHora(),reservaDto.quantidadeLugares());
    }

    public ReservaDto toDto(Reserva reserva){
        return new ReservaDto(
            reserva.getUsuario().getEmailString(),
            reserva.getRestaurante().getCnpjString(),
            reserva.getDataHora(),
            reserva.getQuantidadeLugares(),
            reserva.getStatusString()
        );
    }

}
