package com.fiap.reserva.application.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.fiap.reserva.application.service.ReservaService;
import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

public class ReservaControllerApplication {
    private ReservaService service;
    private UsuarioService usuarioService;
    private RestauranteService restauranteService;
    
    public Reserva cadastrarReserva(final String email, final String cnpj, final String dataHora, final int quantidadeLugares){
        final Reserva reserva = construirReserva(email, cnpj, dataHora, quantidadeLugares);
       
       return service.cadastrarReserva(reserva);
    }

    public Reserva alterarReserva(final String email, final String cnpj, final String dataHora, final int quantidadeLugares) throws BusinessException{
        final Reserva reserva = construirReserva(email, cnpj, dataHora, quantidadeLugares);

        return service.alterarReserva(reserva);
    }

    public void excluirReserva(final String email, final String cnpj, final String dataHora){
        final Reserva reserva = construirReserva(email, cnpj, dataHora, 0);
        service.excluirReserva(reserva);
    }

    public List<Reserva> getBuscarTodasReservaDoUsuarioPeloEmail(final String email) throws BusinessException {
        return service.getBuscarTodasReservaDoUsuarioPeloEmail(new EmailVo(email));
    }

    public List<Reserva> getBuscarTodasRerservasRestaurantePeloCNPJ(final String cnpj) throws BusinessException {
        return service.getBuscarTodasRerservasRestaurantePeloCNPJ(new CnpjVo(cnpj));
    }

     private Reserva construirReserva(final String email, final String cnpj, final String dataHora ,final int quantidadeLugares) {
        try {
            final Usuario usuario = usuarioService.getBuscarPorEmail(new EmailVo(email));
            final Restaurante restaurante = restauranteService.getBuscarPor(new CnpjVo(dataHora));
            
            return new Reserva(usuario, restaurante, LocalDateTime.parse(dataHora), quantidadeLugares);
        } catch (BusinessException e) {
            System.err.println("Erro de negócios: " + e.getMessage());

            return null;
        }
    }
  
}
