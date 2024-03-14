package com.fiap.spring.Controller.Dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.reserva.domain.entity.SituacaoReserva;

public record CriarReservaDTO(
	String emailUsuario,
    String cnpjRestaurante,
    LocalDateTime dataHora
) {
	public ReservaDto toReservaDTO(){
		return new ReservaDto(UUID.randomUUID(), emailUsuario, cnpjRestaurante, dataHora, SituacaoReserva.DISPONIVEL);
	}
}
