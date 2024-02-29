package com.fiap.reserva.application.service;

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
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

public class ReservaService {
    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;

    public ReservaService(ReservaRepository repository, UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Reserva cadastrarReserva(final Reserva reserva){
       return new CadastrarReserva(repository).executar(reserva);
    }

    public Reserva alterarReserva(final Reserva reserva) throws BusinessException{
        return new AlterarReservaRestaurante(repository).executar(reserva);
    }

    public void excluirReserva(final Reserva reserva){
        new ExcluirReservaRestaurante(repository).executar(reserva);
    }

    public List<Reserva> getBuscarTodasReservaDoUsuarioPeloEmail(final EmailVo email) throws BusinessException{
        final Usuario usuario = new BuscarUsuario(usuarioRepository).getUsuarioPor(email);
        return new BuscarReservaUsuario(repository).todasReservasPor(usuario);
    }

    public List<Reserva> getBuscarTodasRerservasRestaurantePeloCNPJ(final CnpjVo cnpj) throws BusinessException{
        final Restaurante restaurante = new BuscarRestaurante(restauranteRepository).getRestaurantePor(cnpj);
        return new BuscarReservaRestaurante(repository).todasReservasPor(restaurante);
    }

   
}
