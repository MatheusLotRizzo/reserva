package com.fiap.reserva.application.usecase.reserva;

import static org.junit.jupiter.api.Assertions.*;
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

class CadastrarReservaTest {

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
	void naoDeveSalvarReservaSeNaoPassadaPorParametro() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> new CadastrarReserva(repository).executar(null));
		assertEquals("Preencha uma reserva para ser salva", throwable.getMessage());
	}
	
	@Test
	void deveSalvarReserva() throws BusinessException {
		final Reserva reserva = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.DISPONIVEL);
		
		when(repository.criar(reserva)).thenReturn(reserva);
		
		final Reserva esperado = new CadastrarReserva(repository).executar(reserva);
		
		assertNotNull(esperado);
		assertEquals(SituacaoReserva.RESERVADO, esperado.getSituacao());
	}
}
