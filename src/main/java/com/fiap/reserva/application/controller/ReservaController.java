package com.fiap.reserva.application.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.fiap.reserva.application.usecase.reserva.AlterarReservaRestaurante;
import com.fiap.reserva.application.usecase.reserva.BuscarReservaRestaurante;
import com.fiap.reserva.application.usecase.reserva.BuscarReservaUsuario;
import com.fiap.reserva.application.usecase.reserva.CadastrarReserva;
import com.fiap.reserva.application.usecase.reserva.ExcluirReservaRestaurante;
import com.fiap.reserva.application.usecase.restaurante.BuscarRestaurante;
import com.fiap.reserva.application.usecase.usuario.BuscarUsuario;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;

public class ReservaController {
    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;

    public ReservaController(ReservaRepository repository, UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public void cadastrarReserva(final String email, final String cnpj, final String dataHora, final int quantidadeLugares){
        final Reserva reserva = construirReserva(email, cnpj, dataHora, quantidadeLugares);
        
        new CadastrarReserva(repository).executar(reserva);
    }

    public void alterarReserva(final String email, final String cnpj, final String dataHora, final int quantidadeLugares) throws BusinessException{
        final Reserva reserva = construirReserva(email, cnpj, dataHora, quantidadeLugares);
      
        new AlterarReservaRestaurante(repository).executar(reserva);
    }

    public void excluirReserva(final String email, final String cnpj, final String dataHora){
        new ExcluirReservaRestaurante(repository).executar(email, cnpj, dataHora);
    }

    public List<Reserva> getBuscarTodasReservaDoUsuarioPeloEmail(final String email) throws BusinessException{
        final Usuario usuario = new BuscarUsuario(usuarioRepository).getUsuarioPor(email);
        return new BuscarReservaUsuario(repository).todasReservasPor(usuario);
    }

    public List<Reserva> getBuscarTodasRerservasRestaurantePeloCNPJ(final String cnpj) throws BusinessException{
        final Restaurante restaurante = new BuscarRestaurante(restauranteRepository).getRestaurantePor(cnpj);
        return new BuscarReservaRestaurante(repository).todasReservasPor(restaurante);
    }

    private Reserva construirReserva(final String email, final String cnpj, final String dataHora ,final int quantidadeLugares) {
        try {
            final Usuario usuario = new BuscarUsuario(usuarioRepository).getUsuarioPor(email);
            final Restaurante restaurante = new BuscarRestaurante(restauranteRepository).getRestaurantePor(cnpj);
            final Reserva reserva = new Reserva(usuario, restaurante, LocalDateTime.parse(dataHora), quantidadeLugares);
            return reserva;
        } catch (BusinessException e) {
            System.err.println("Erro de negócios: " + e.getMessage());

            return null;
        }
    }
}
