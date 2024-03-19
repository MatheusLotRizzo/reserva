package com.fiap.reserva.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
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

class ReservaServiceTest {

	@InjectMocks
	private ReservaService service;
	@Mock
	private ReservaRepository repository;
	@Mock
	private RestauranteService restauranteService;
	
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
	void naoDeveCadastrarReservaSeNaoInformada() throws BusinessException {
		final Throwable throwable = assertThrows(BusinessException.class, () -> service.criarReserva(null));
		assertEquals("Informe uma reserva para ser cadastrada", throwable.getMessage());
	}
	
	@Test
	void naoDeveCadastrarReservaCasoRestauranteNaoPossuaMesasDisponiveis() throws BusinessException {
		final Throwable throwable = assertThrows(BusinessException.class, () -> {
			final List<Reserva> reservas = getReservasMock();
			final Reserva rDisponivel  = reservas.stream().filter(r -> r.getSituacao() == SituacaoReserva.DISPONIVEL).findFirst().get();
			when(repository.buscarTodasPor(any(Restaurante.class))).thenReturn(reservas);
			when(restauranteService.obterLocacaoMaxRestaurante(any(Restaurante.class))).thenReturn(0);
				
			service.criarReserva(rDisponivel);
			
		});
		
		assertEquals("Não existe disponibilidade para este dia", throwable.getMessage());
	}
	
	@Test
	void deveCadastrarReserva() throws BusinessException {
		final List<Reserva> reservas = getReservasMock();
		final Reserva rDisponivel  = reservas.stream().filter(r -> r.getSituacao() == SituacaoReserva.DISPONIVEL).findFirst().get();
		when(repository.buscarTodasPor(any(Restaurante.class))).thenReturn(reservas);
		when(repository.criar(rDisponivel)).thenReturn(rDisponivel);
		when(restauranteService.obterLocacaoMaxRestaurante(any(Restaurante.class))).thenReturn(2);
			
		final Reserva retorno = service.criarReserva(rDisponivel);
		
		verify(repository).buscarTodasPor(any(Restaurante.class));
		verify(repository).criar(rDisponivel);
		verify(restauranteService).obterLocacaoMaxRestaurante(any(Restaurante.class));
		
		assertNotNull(retorno);
		assertEquals(SituacaoReserva.RESERVADO, retorno.getSituacao());
	}

	private List<Reserva> getReservasMock() throws BusinessException{
		final Reserva rReservado = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.RESERVADO
		);
		
		final Reserva rConcluido = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.CONCLUIDO
		);
		
		final Reserva rCancelada = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.CANCELADO
		);
		
		final Reserva rDisponivel = new Reserva(
			UUID.randomUUID(), 
			new Usuario("teste@teste.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(), 
			SituacaoReserva.DISPONIVEL
		);
		
		return java.util.Arrays.asList(rReservado, rCancelada, rConcluido, rDisponivel);
	}
}
