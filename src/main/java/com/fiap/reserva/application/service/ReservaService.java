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
        int lotacaoRestaurante = restauranteService.obterLocacaoMaxRestaurante(reserva.getRestaurante());
        if ( lotacaoRestaurante > 0){
            throw new BusinessException("Não existe disponibilidade para este dia");
        }

        return new CadastrarReserva(repository).executar(reserva);
    }

    public Reserva alterarReserva(final Reserva reserva) throws BusinessException{
        return new AlterarReservaRestaurante(repository).executar(reserva);
    }

    public Reserva cancelarReserva(final Reserva reserva) throws BusinessException{
        Reserva reservaCancelada = reserva;
        reservaCancelada.cancelar();
        return new AlterarReservaRestaurante(repository).executar(reservaCancelada);
    }

    public Reserva baixarReserva(final Reserva reserva) throws BusinessException{
        Reserva reservaCancelada = reserva;
        reservaCancelada.baixarReserva();
        return new AlterarReservaRestaurante(repository).executar(reservaCancelada);
    }

    public void excluirReserva(final Reserva reserva) throws BusinessException{
        new ExcluirReservaRestaurante(repository).executar(reserva);
    }

    public List<Reserva> getBuscarTodasReservaDoUsuarioPeloEmail(final EmailVo email) throws BusinessException{
        final Usuario usuario = usuarioService.getBuscarPor(new Usuario(email.getEndereco()));
        return new BuscarReservaUsuario(repository).executar(usuario);
    }

    public List<Reserva> getBuscarTodasRerservasRestaurantePeloCNPJ(final CnpjVo cnpj) throws BusinessException{
        final Restaurante restaurante = restauranteService.getBuscarPor(cnpj);
        return new BuscarReservaRestaurante(repository).executar(restaurante);
    }

    public List<Reserva> getBuscarTodasRerservas(final Reserva reserva) throws BusinessException{
        return new BuscarReserva(repository).getTodasReserva(reserva);
    }

    public Reserva getObter(final Reserva reserva) throws BusinessException{
        return new BuscarReserva(repository).getReserva(reserva);
    }

}
