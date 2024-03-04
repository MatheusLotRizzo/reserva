package com.fiap;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fiap.reserva.application.usecase.reserva.CadastrarReserva;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.infra.jdbc.H2Connection;
import com.fiap.reserva.infra.jdbc.reserva.ReservaRepositoryImpl;
import com.fiap.reserva.infra.jdbc.restaurante.RestauranteRepositoryImpl;
import com.fiap.reserva.infra.jdbc.usuario.UsuarioRepositoryImpl;

@SpringBootApplication
public class ReservaApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(ReservaApplication.class, args);
	}

	void insereReservaNoBanco() throws SQLException, BusinessException {
		Connection connection = H2Connection.conectar();
		ReservaRepository reservaRepository = new ReservaRepositoryImpl(connection);
		CadastrarReserva cadastrarReserva = new CadastrarReserva(reservaRepository);
		
		final Usuario usuario = new Usuario("Weslei","fualno@com.br","");
		new UsuarioRepositoryImpl(connection).cadastrar(usuario);

		final Restaurante restaurante = new Restaurante("12345678900000", "nome_restaurante", null, null, 100, TipoCozinha.BRASILEIRA);
		new RestauranteRepositoryImpl(connection).cadastrar(restaurante);
		final Reserva reserva = new Reserva(
			UUID.randomUUID(), 
			usuario,
			restaurante,
			LocalDateTime.now().plusHours(1),
			SituacaoReserva.RESERVADO);

		cadastrarReserva.executar(reserva);

		connection.close();
	}
}
