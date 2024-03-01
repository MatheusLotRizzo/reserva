package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.reserva.*;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

import java.util.List;

public class ReservaService {
    private final ReservaRepository repository;
    private RestauranteService restauranteService;
    private UsuarioService usuarioService;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public Reserva cadastrarReserva(final Reserva reserva) throws BusinessException{
        Integer lotacaoReserva = new ObterLotacaoaReserva(repository).executar(reserva);
        Integer lotacaoRestaurante = restauranteService.obterLocacaoMaxRestaurante(reserva.getRestaurante());
        if (lotacaoReserva >= lotacaoRestaurante){
            throw new BusinessException("Não existe disponibilidade para este dia");
        }
        return new CadastrarReserva(repository).executar(reserva);
    }

    public Reserva alterarReserva(final Reserva reserva) throws BusinessException{
        return new AlterarReservaRestaurante(repository).executar(reserva);
    }

    public Reserva cancelarReserva(Reserva reserva) throws BusinessException{
        reserva.cancelar();
        return new CancelarReservaRestaurante(repository).executar(reserva);
    }

    public Reserva baixarReserva(Reserva reserva) throws BusinessException{
        reserva.baixarReserva();
        return new BaixarReservaRestaurante(repository).executar(reserva);
    }

    public void excluirReserva(final Reserva reserva) throws BusinessException{
        new ExcluirReservaRestaurante(repository).executar(reserva);
    }

    public List<Reserva> getBuscarTodasReservaDoUsuarioPeloEmail(final EmailVo email) throws BusinessException{
        final Usuario usuario = usuarioService.getBuscarPorEmail(email);
        return new BuscarReservaUsuario(repository).todasReservasPor(usuario);
    }

    public List<Reserva> getBuscarTodasRerservasRestaurantePeloCNPJ(final CnpjVo cnpj) throws BusinessException{
        final Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
        return new BuscarReservaRestaurante(repository).todasReservasPor(restaurante);
    }

    public List<Reserva> getBuscarTodasRerservas(final Reserva reserva) throws BusinessException{
        return new BuscarReserva(repository).getTodasReserva(reserva);
    }

    public Reserva getObter(final Reserva reserva) throws BusinessException{
        return new BuscarReserva(repository).getReserva(reserva);
    }

}
