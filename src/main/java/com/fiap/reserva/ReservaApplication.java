package com.fiap.reserva;

import com.fiap.reserva.application.usecase.CadastrarReserva;
import com.fiap.reserva.domain.entity.*;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.reserva.infra.ReservaEmMemoria;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class ReservaApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ReservaApplication.class, args);
		ReservaRepository reservaRepository = new ReservaEmMemoria();
		CadastrarReserva cadastrarReserva = new CadastrarReserva(reservaRepository);

		Usuario usuario = new Usuario("Weslei",null);
		Restaurante restaurante = new Restaurante(null, "nome_restaurante", null, null, 100, TipoCozinha.BRASILEIRA);
		Reserva reserva = new Reserva(usuario,restaurante,LocalDateTime.now().plusHours(1),2);

		cadastrarReserva.executar(reserva);
		System.out.println(reservaRepository.buscarTodos(null));

	}

}
