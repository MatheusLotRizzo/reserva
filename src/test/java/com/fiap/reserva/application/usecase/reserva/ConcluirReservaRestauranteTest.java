package com.fiap.reserva.application.usecase.reserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;

class ConcluirReservaRestauranteTest {


	@Mock
	private ReservaRepository repository;
	private AutoCloseable autoCloseable;
	
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	void naoDeveConcluirReservaCasoNaoSejaPassadaViaParametro() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> new ConcluirReservaRestaurante(repository).executar(null));
		assertEquals("Informe a reserva para ser concluida", throwable.getMessage());
	}
	
	@Test
	void naoDeveConcluirReservaCasoSituacaoDisponivel() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> {
			final Reserva reserva = new Reserva(
					UUID.randomUUID(), 
					new Usuario("teste@teste.com"), 
					new Restaurante("12345678900000"), 
					LocalDateTime.now(), 
					SituacaoReserva.DISPONIVEL);
				
				when(repository.alterar(reserva)).thenReturn(reserva);
				
				new ConcluirReservaRestaurante(repository).executar(reserva);
		});
		assertEquals("Não é possivel cancelar reserva uma disponivel", throwable.getMessage());
	}
	
	@Test
	void naoDeveConcluirReservaCasoSituacaoCancelada() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> {
			final Reserva reserva = new Reserva(
					UUID.randomUUID(), 
					new Usuario("teste@teste.com"), 
					new Restaurante("12345678900000"), 
					LocalDateTime.now(), 
					SituacaoReserva.CANCELADO);
				
				when(repository.alterar(reserva)).thenReturn(reserva);
				
				new ConcluirReservaRestaurante(repository).executar(reserva);
		});
		assertEquals("Esta reserva ja esta cancelada", throwable.getMessage());
	}
	
	@Test
	void naoDeveConcluirReservaCasoSituacaoConcluida() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> {
			final Reserva reserva = new Reserva(
					UUID.randomUUID(), 
					new Usuario("teste@teste.com"), 
					new Restaurante("12345678900000"), 
					LocalDateTime.now(), 
					SituacaoReserva.CONCLUIDO);
				
				when(repository.alterar(reserva)).thenReturn(reserva);
				
				new ConcluirReservaRestaurante(repository).executar(reserva);
		});
		assertEquals("Esta reserva ja esta concluida", throwable.getMessage());
	}
	
	@Test
	void deveConcluirReserva() throws BusinessException {
		final Reserva reserva = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.RESERVADO);
		
		when(repository.alterar(reserva)).thenReturn(reserva);
		
		new ConcluirReservaRestaurante(repository).executar(reserva);
		
		verify(repository).alterar(reserva);
		
		assertEquals(SituacaoReserva.CONCLUIDO, reserva.getSituacao());
		
	}

}
