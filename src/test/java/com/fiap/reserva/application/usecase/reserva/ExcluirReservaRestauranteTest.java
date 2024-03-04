package com.fiap.reserva.application.usecase.reserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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

class ExcluirReservaRestauranteTest {

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
	void naoDeveAlterarReservaCasoNaoSejaPassadaViaParametro() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> new ExcluirReservaRestaurante(repository).executar(null));
		assertEquals("Informe a reserva para ser excluida", throwable.getMessage());
	}
	
	@Test
	void deveAlterarReserva() throws BusinessException {
		final Reserva reserva = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.RESERVADO);
		
		doNothing().when(repository).excluir(reserva);
		
		new ExcluirReservaRestaurante(repository).executar(reserva);
		
		verify(repository).excluir(reserva);
	}

}
