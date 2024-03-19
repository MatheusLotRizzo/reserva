package com.fiap.reserva.application.usecase.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

class BuscarReservaTest {

	@Mock
	private ReservaRepository repository;
	private AutoCloseable autoCloseable;
	
	@InjectMocks
	private BuscarReserva buscarReserva;
	
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	void naoDeveBuscarReservaPorNumeroReservaCasoNaoSejaPassadaViaParametro() {
		Throwable throwable = assertThrows(BusinessException.class, () -> buscarReserva.executar(null));
		assertEquals("Número da reserva é obrigatório", throwable.getMessage());
	}
	
	@Test
	void deveBuscarReservasDoUsuario() throws BusinessException {
		final UUID numeroReserva  = UUID.randomUUID();
		final Reserva reserva = new Reserva(
			numeroReserva, 
			new Usuario("teste@teste.com.br"), 
			new Restaurante("12345678900909"), 
			LocalDateTime.now(), 
			SituacaoReserva.RESERVADO
		);
		
		when(repository.buscarPor(numeroReserva)).thenReturn(reserva);
		final Reserva retorno = buscarReserva.executar(numeroReserva);
		
		assertEquals(reserva, retorno);
		verify(repository).buscarPor(numeroReserva);
	}

}
