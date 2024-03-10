package com.fiap.reserva.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.application.service.ReservaService;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.ReservaDto;

public class ReservaControllerApplication {
    private final ReservaService service;

    public ReservaControllerApplication(ReservaService service) {
		this.service = service;
	}

	public ReservaDto criarReserva(ReservaDto reservaDto)throws BusinessException{
        final Reserva reservaEntity = service.criarReserva(reservaDto.toEntity());
        
        return toReservaDTO(reservaEntity);
    }

    public ReservaDto alterarReserva(final ReservaDto reservaDto) throws BusinessException{
        final Reserva reservaEntity = service.alterarReserva(reservaDto.toEntity());
        
        return toReservaDTO(reservaEntity);
    }

    public void cancelarReserva(final ReservaDto reservaDto) throws BusinessException{
        service.cancelarReserva(reservaDto.toEntity());
    }
    
    public void concluirReserva(final ReservaDto reservaDto) throws BusinessException{
    	service.concluirReserva(reservaDto.toEntity());
    }

    public List<ReservaDto> getBuscarTodasReservaDoUsuarioPeloEmail(final String email) throws BusinessException {
        final List<Reserva> reservas = service.getBuscarTodasReservaDoUsuarioPeloEmail(new EmailVo(email));

        return reservas.stream()
                .map(this::toReservaDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> getBuscarTodasRerservasRestaurantePeloCNPJ(final String cnpj) throws BusinessException {
        final List<Reserva> reservas = service.getBuscarTodasRerservasRestaurantePeloCNPJ(new CnpjVo(cnpj));

        return reservas.stream()
                .map(this::toReservaDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> getBuscarTodas(final ReservaDto reservaDto) throws BusinessException {
        final List<Reserva> reservas = service.getBuscarTodasRerservas(reservaDto.toEntity());

        return reservas.stream()
                .map(this::toReservaDTO)
                .collect(Collectors.toList());
    }

    private ReservaDto toReservaDTO(final Reserva reservaEntity) {
        return new ReservaDto(
            reservaEntity.getNumeroReserva(),
            reservaEntity.getUsuario().getEmailString(), 
            reservaEntity.getRestaurante().getCnpjString(),
            reservaEntity.getDataHora(), 
            reservaEntity.getSituacao()
        );
    }
}
