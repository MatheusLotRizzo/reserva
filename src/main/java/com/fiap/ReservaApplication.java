package com.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaApplication.class, args);
	/* 	ReservaRepository reservaRepository = new ReservaEmMemoria();
		CadastrarReserva cadastrarReserva = new CadastrarReserva(reservaRepository);

		Usuario usuario = new Usuario("Weslei",null);
		Restaurante restaurante = new Restaurante(null, "nome_restaurante", null, null, 100, TipoCozinha.BRASILEIRA);
		Reserva reserva = new Reserva(usuario,restaurante,LocalDateTime.now().plusHours(1),2);

		cadastrarReserva.executar(reserva);
		System.out.println(reservaRepository.buscarTodos(null));
*/
	}

}
