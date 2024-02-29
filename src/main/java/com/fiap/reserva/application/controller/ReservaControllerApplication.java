package com.fiap.reserva.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.application.service.ReservaService;
import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.ReservaDto;

public class ReservaControllerApplication {
    private ReservaService service;
    private UsuarioService usuarioService;
    private RestauranteService restauranteService;
    
    public ReservaDto cadastrarReserva(ReservaDto reservaDto)throws BusinessException{
        final Reserva reserva = construirReserva(reservaDto);
       
       return construirReservaDto(service.cadastrarReserva(reserva));
    }

    public ReservaDto alterarReserva(final ReservaDto reservaDto) throws BusinessException{
        final Reserva reserva = construirReserva(reservaDto);

        return construirReservaDto(service.alterarReserva(reserva));
    }

    public void excluirReserva(final ReservaDto reservaDto){
        final Reserva reserva = construirReserva(reservaDto);
        service.excluirReserva(reserva);
    }

    public List<ReservaDto> getBuscarTodasReservaDoUsuarioPeloEmail(final String email) throws BusinessException {
        List<Reserva> reservas = service.getBuscarTodasReservaDoUsuarioPeloEmail(new EmailVo(email));

        return reservas.stream()
                .map(this::construirReservaDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> getBuscarTodasRerservasRestaurantePeloCNPJ(final String cnpj) throws BusinessException {
        List<Reserva> reservas = service.getBuscarTodasRerservasRestaurantePeloCNPJ(new CnpjVo(cnpj));

        return reservas.stream()
                .map(this::construirReservaDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> getBuscarTodas(final ReservaDto reservaDto) throws BusinessException {
        List<Reserva> reservas = service.getBuscarTodasRerservas(construirReserva(reservaDto));

        return reservas.stream()
                .map(this::construirReservaDto)
                .collect(Collectors.toList());
    }

    public ReservaDto getObter(final ReservaDto reservaDto) throws BusinessException {
        return construirReservaDto(service.getObter(construirReserva(reservaDto)) );
    }

     private Reserva construirReserva(final ReservaDto reservaDto) {
        try {
            final Usuario usuario = usuarioService.getBuscarPorEmail(new EmailVo(reservaDto.emailUsuario()));
            final Restaurante restaurante = restauranteService.getBuscarPor(new CnpjVo(reservaDto.cnpjRestaurante()));
            
            return new Reserva(usuario, restaurante, reservaDto.dataHora(), reservaDto.quantidadeLugares());
        } catch (BusinessException e) {
            System.err.println("Erro de negócios: " + e.getMessage());

            return null;
        }
    }

    private ReservaDto construirReservaDto(final Reserva reserva){
        return reserva.toDto();
    }
  
}
