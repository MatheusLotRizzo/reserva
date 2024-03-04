package com.fiap.reserva.application.usecase.reserva;

import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

class BuscarReservaUsuarioTest {

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
	void naoDeveBuscarReservaPorUsuarioCasoNaoSejaPassadaViaParametro() {
		final Throwable throwable = assertThrows(BusinessException.class, () -> new BuscarReservaUsuario(repository).executar(null));
		assertEquals("Usuario é obrigatorio para realizar a busca!!", throwable.getMessage());
	}
	
	@Test
	void deveBuscarReservasDoUsuario() throws BusinessException {
		final Usuario usuario = new Usuario("teste@teste.com");
		final Reserva reserva1 = new Reserva(
			UUID.randomUUID(), 
			usuario, 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.RESERVADO
		);
		
		final Reserva reserva2 = new Reserva(
			UUID.randomUUID(), 
			usuario, 
			new Restaurante("12345678910010"), 
			LocalDateTime.now(), 
			SituacaoReserva.RESERVADO
		);
		
		when(repository.buscarTodasPor(usuario)).thenReturn(Arrays.asList(reserva2, reserva1));
		
		final List<Reserva> esperado = new BuscarReservaUsuario(repository).executar(usuario);
		
		assertNotNull(esperado);
		assertThatCollection(esperado).hasSize(2);
		assertThatCollection(esperado).filteredOnAssertions(reserva -> reserva.getUsuario().equals(usuario));
		
		verify(repository).buscarTodasPor(usuario);
	}

}
