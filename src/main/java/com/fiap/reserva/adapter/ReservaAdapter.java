package com.fiap.reserva.adapter;

import java.util.List;
import com.fiap.reserva.application.controller.ReservaDto;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;

public final class ReservaAdapter {

    private ReservaAdapter(){}
    
    public static Reserva toReserva(ReservaDto reservaDto){
        return new Reserva(
            new Usuario(reservaDto.emailUsuario()), 
            new Restaurante(reservaDto.cnpjRestaurante()), 
            reservaDto.dataHora(),
            reservaDto.quantidadeLugares()
        );
    }

    public static List<ReservaDto> toDtos(List<Reserva> reservas){
        return reservas.stream().map(ReservaAdapter::toDto).toList();
    }

    public static ReservaDto toDto(Reserva reserva){
        return new ReservaDto(
            reserva.getUsuario().getEmailString(),
            reserva.getRestaurante().getCnpjString(),
            reserva.getDataHora(),
            reserva.getQuantidadeLugares(),
            reserva.getStatusString()
        );
    }


}
