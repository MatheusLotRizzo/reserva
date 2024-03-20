package com.fiap.spring.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.spring.Controller.Dto.ReservaDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import com.fiap.spring.conf.DataSourceMock;
import com.fiap.spring.conf.InjecaoDependencia;

import infraTest.UtilsTest;

@ContextConfiguration(classes = ReservaControllerSpring.class)
@ExtendWith(SpringExtension.class)
@Import({
	DataSourceMock.class,
	InjecaoDependencia.class,
})
class ReservaControllerSpringTest {
	
	private AutoCloseable autoCloseable;
	private MockMvc mockMvc;
	
	@MockBean
	private ReservaRepository repository;
	
	@MockBean
	private RestauranteService restauranteService; 
	
	@MockBean
	private UsuarioService usuarioService;
	
	@Autowired
	private ReservaControllerSpring controller;
	
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	@Nested
	class CriarReserva{
		@Test
		void deveCriarReserva() throws Exception {
			final ReservaDto reservaDto = new ReservaDto(
				UUID.randomUUID(), 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.DISPONIVEL
			);
			
			when(restauranteService.obterLocacaoMaxRestaurante(any()))
				.thenReturn(1);
			
			when(repository.buscarTodasPor(any(Restaurante.class)))
				.thenReturn(Arrays.asList(reservaDto.toEntity()));
			
			when(repository.criar(any()))
				.thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.post("/reserva")
				.content(UtilsTest.convertJson(reservaDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			).andExpect(MockMvcResultMatchers.status().isCreated());
			
			verify(restauranteService).obterLocacaoMaxRestaurante(any());
			verify(repository).buscarTodasPor(any(Restaurante.class));
			verify(repository).criar(any());
		}
		
		@Test
		void naoDeveCriarReservaCasoMesasDisponiveisIgualZero() throws Exception {
			final ReservaDto reservaDto = new ReservaDto(
					UUID.randomUUID(), 
					"denis.benjamim@gmail.com", 
					"71736952000116", 
					LocalDateTime.of(2024, 3, 10, 12, 0), 
					SituacaoReserva.DISPONIVEL
				);
			when(restauranteService.obterLocacaoMaxRestaurante(any()))
				.thenReturn(0);
			when(repository.buscarTodasPor(any(Restaurante.class)))
				.thenReturn(Arrays.asList(reservaDto.toEntity()));
			
			mockMvc.perform(MockMvcRequestBuilders
				.post("/reserva")
				.content(UtilsTest.convertJson(reservaDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().json(
				UtilsTest.convertJson(MessageErrorHandler.create("Não existe disponibilidade para este dia")))
			);
			
			verify(repository).buscarTodasPor(any(Restaurante.class));
			verify(restauranteService).obterLocacaoMaxRestaurante(any());
		}
		
		@Test
		void naoDeveCriarReservaCasoRestauranteJaTenhaAtingidoCapacidadeMaxima() throws Exception {
			final ReservaDto reservaDto = new ReservaDto(
					UUID.randomUUID(), 
					"denis.benjamim@gmail.com", 
					"71736952000116", 
					LocalDateTime.of(2024, 3, 10, 12, 0), 
					SituacaoReserva.DISPONIVEL
				);
			
			final Reserva reservado = reservaDto.toEntity();
			reservado.reservar();
			
			when(restauranteService.obterLocacaoMaxRestaurante(any()))
				.thenReturn(1);
			when(repository.buscarTodasPor(any(Restaurante.class)))
				.thenReturn(Arrays.asList(reservado));
			
			mockMvc.perform(MockMvcRequestBuilders
				.post("/reserva")
				.content(UtilsTest.convertJson(reservaDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().json(
				UtilsTest.convertJson(MessageErrorHandler.create("Não existe disponibilidade para este dia")))
			);
			
			verify(repository).buscarTodasPor(any(Restaurante.class));
			verify(restauranteService).obterLocacaoMaxRestaurante(any());
		}
	}
	
	@Nested
	class ConcluirReserva{
		@Test
		void deveConcluirReserva() throws Exception {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.RESERVADO
			);
			
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/concluir/{numeroReserva}", numeroReserva)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			).andExpect(MockMvcResultMatchers.status().isNoContent());
			
			verify(repository).alterar(any());
		}
		
		@Test
		void naoDeveConcluirReservaDisponivel() throws Exception {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.DISPONIVEL
			);
			
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/concluir/{numeroReserva}", numeroReserva)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json(UtilsTest.convertJson(MessageErrorHandler.create("Não é possivel cancelar reserva uma disponivel")))
			);
		}
		
		@Test
		void naoDeveConcluirReservaCancelada() throws Exception  {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.CANCELADO
			);
			
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/concluir/{numeroReserva}", numeroReserva)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json(UtilsTest.convertJson(MessageErrorHandler.create("Esta reserva ja esta cancelada")))
			);
		}
		
		@Test
		void naoDeveConcluirReservaConcluida() throws Exception {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.CONCLUIDO
			);
			
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/concluir/{numeroReserva}", numeroReserva)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json(UtilsTest.convertJson(MessageErrorHandler.create("Esta reserva ja esta concluida")))
			);
		}
	}
	
	@Nested
	class CancelarReserva{
		@Test
		void deveCancelarReserva() throws Exception {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.RESERVADO
			);
				
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/cancelar/{numeroReserva}",numeroReserva.toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			).andExpect(MockMvcResultMatchers.status().isNoContent());
			
			verify(repository).alterar(any());
		}
		
		@Test
		void naoDeveCancelarReservaConcluida() throws Exception  {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.CONCLUIDO
			);
			
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/cancelar/{numeroReserva}", numeroReserva)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json(UtilsTest.convertJson(MessageErrorHandler.create("Esta reserva ja esta concluida")))
			);
		}
		
		@Test
		void naoDeveCancelarReservaCancelada() throws Exception {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.CANCELADO
			);
			
			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/cancelar/{numeroReserva}", numeroReserva)
				.content(UtilsTest.convertJson(reservaDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json(UtilsTest.convertJson(MessageErrorHandler.create("Esta reserva ja esta cancelada")))
			);
		}
		
		@Test
		void naoDeveCancelarReservaDisponivel() throws Exception {
			final UUID numeroReserva = UUID.randomUUID();
			final ReservaDto reservaDto = new ReservaDto(
				numeroReserva, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				SituacaoReserva.DISPONIVEL
			);

			when(repository.buscarPor(numeroReserva)).thenReturn(reservaDto.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.patch("/reserva/cancelar/{numeroReserva}", numeroReserva)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json(UtilsTest.convertJson(MessageErrorHandler.create("Não é possivel cancelar reserva uma disponivel")))
			);
		}
	}
	
	@Nested
	class BuscarReserva{
		@Test
		void deveBuscarTodasReservasDoRestaurante() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDisponiveisRestauranteDto = Arrays.asList(reserva1, reserva2, reserva3, reserva4, reserva5);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}",cnpjRestaurante)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDisponiveisRestauranteDto)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}
		
		@Test
		void deveBuscarTodasReservasDisponiveisDoRestaurantePeloCNPJ() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDisponiveisRestauranteDto = Arrays.asList(reserva1, reserva5);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}/situacao/{situacao-reserva}", cnpjRestaurante, SituacaoReserva.DISPONIVEL)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDisponiveisRestauranteDto)))
			.andDo(MockMvcResultHandlers.print())
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}
		
		@Test
		void deveBuscarTodasReservasCancelasDoRestaurantePeloCNPJ() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDisponiveisRestauranteDto = Arrays.asList(reserva2, reserva5);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}/situacao/{situacao-reserva}", cnpjRestaurante, SituacaoReserva.CANCELADO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDisponiveisRestauranteDto)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}
		
		@Test
		void deveBuscarTodasReservasConcluidasDoRestaurantePeloCNPJ() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDisponiveisRestauranteDto = Arrays.asList(reserva3, reserva5);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}/situacao/{situacao-reserva}", cnpjRestaurante, SituacaoReserva.CONCLUIDO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDisponiveisRestauranteDto)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}
		
		@Test
		void deveBuscarTodasReservasReservadasDoRestaurantePeloCNPJ() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDisponiveisRestauranteDto = Arrays.asList(reserva4, reserva5);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}/situacao/{situacao-reserva}", cnpjRestaurante, SituacaoReserva.RESERVADO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDisponiveisRestauranteDto)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}

		@Test
		void deveBuscarReservasPeloUsuario() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final List<Reserva> reservas = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDTO = Arrays.asList(reserva1, reserva2, reserva3, reserva4, reserva5);
			final Usuario usuario = new UsuarioDto("Denis Alves", emailUsuario, "13997279686").toEntity();
			
			
			when(repository.buscarTodasPor(usuario)).thenReturn(reservas);
			when(usuarioService.getBuscarPor(usuario.getEmail())).thenReturn(usuario);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/usuario/{email}", emailUsuario)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDTO)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(usuarioService).getBuscarPor(usuario.getEmail());
			verify(repository).buscarTodasPor(usuario);
		}
				
		@Test
		void deveBuscarReservasDoUsuarioPelaSituacaoDisponivel() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final List<Reserva> reservas = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDTO = Arrays.asList(reserva1, reserva5);
			final Usuario usuario = new UsuarioDto("Denis Alves", emailUsuario, "13997279686").toEntity();
			
			when(repository.buscarTodasPor(usuario)).thenReturn(reservas);
			when(usuarioService.getBuscarPor(usuario.getEmail())).thenReturn(usuario);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/usuario/{email}/{situacao-reserva}", emailUsuario, SituacaoReserva.DISPONIVEL)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDTO)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(usuarioService).getBuscarPor(usuario.getEmail());
			verify(repository).buscarTodasPor(usuario);
		}
		
		@Test
		void deveBuscarReservasDoUsuarioPelaSituacaoCancelado() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final List<Reserva> reservas = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDTO = Arrays.asList(reserva2, reserva5);
			final Usuario usuario = new UsuarioDto("Denis Alves", emailUsuario, "13997279686").toEntity();
			
			when(repository.buscarTodasPor(usuario)).thenReturn(reservas);
			when(usuarioService.getBuscarPor(usuario.getEmail())).thenReturn(usuario);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/usuario/{email}/{situacao-reserva}", emailUsuario, SituacaoReserva.CANCELADO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDTO)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(usuarioService).getBuscarPor(usuario.getEmail());
			verify(repository).buscarTodasPor(usuario);
		}
		
		@Test
		void deveBuscarReservasDoUsuarioPelaSituacaoConcluido() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final List<Reserva> reservas = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDTO = Arrays.asList(reserva3, reserva5);
			final Usuario usuario = new UsuarioDto("Denis Alves", emailUsuario, "13997279686").toEntity();
			
			when(repository.buscarTodasPor(usuario)).thenReturn(reservas);
			when(usuarioService.getBuscarPor(usuario.getEmail())).thenReturn(usuario);
			
			mockMvc.perform(MockMvcRequestBuilders
					.get("/reserva/usuario/{email}/{situacao-reserva}", emailUsuario, SituacaoReserva.CONCLUIDO)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDTO)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(usuarioService).getBuscarPor(usuario.getEmail());
			verify(repository).buscarTodasPor(usuario);
		}
		
		@Test
		void deveBuscarReservasDoUsuarioPelaSituacaoReservado() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			
			final ReservaDto reserva1 = criarReservaSituacaoPersonalizada(SituacaoReserva.DISPONIVEL);
			final ReservaDto reserva2 = criarReservaSituacaoPersonalizada(SituacaoReserva.CANCELADO);
			final ReservaDto reserva3 = criarReservaSituacaoPersonalizada(SituacaoReserva.CONCLUIDO);
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final ReservaDto reserva5 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			final List<Reserva> reservas = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDTO = Arrays.asList(reserva4, reserva5);
			final Usuario usuario = new UsuarioDto("Denis Alves", emailUsuario, "13997279686").toEntity();
			
			when(repository.buscarTodasPor(usuario)).thenReturn(reservas);
			when(usuarioService.getBuscarPor(usuario.getEmail())).thenReturn(usuario);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/usuario/{email}/{situacao-reserva}", emailUsuario, SituacaoReserva.RESERVADO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDTO)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(usuarioService).getBuscarPor(usuario.getEmail());
			verify(repository).buscarTodasPor(usuario);
		}
		
		@Test
		void deveBuscarReservaPeloNumeroReserva() throws Exception {
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			
			when(repository.buscarPor(reserva4.numeroReserva())).thenReturn(reserva4.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/{numeroReserva}", reserva4.numeroReserva())
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reserva4)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(repository).buscarPor(reserva4.numeroReserva());
		}
		
		@Test
		void naoDeveDevolverReservaCasoNumeroReservaInexistente() throws Exception {
			final UUID numeroReservaProcurado = UUID.randomUUID();
			final ReservaDto reserva4 = criarReservaSituacaoPersonalizada(SituacaoReserva.RESERVADO);
			
			when(repository.buscarPor(reserva4.numeroReserva())).thenReturn(reserva4.toEntity());
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/{numeroReserva}", numeroReservaProcurado)
			)
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(MessageErrorHandler.create("Reserva não encontrada"))))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(repository).buscarPor(numeroReservaProcurado);
		}
		
		@Test
		void deveBuscarReservasDoRestaurantePelaData() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 11, 8, 0));
			final ReservaDto reserva2 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 11, 9, 0));
			final ReservaDto reserva3 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 10, 10, 0));
			final ReservaDto reserva4 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 10, 10, 30));
			final ReservaDto reserva5 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 12, 12, 0));
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final List<ReservaDto> reservasDisponiveisRestauranteDto = Arrays.asList(reserva3, reserva4);
			final ReservaDto parametrosBusca = new ReservaDto(null, null, cnpjRestaurante, null, SituacaoReserva.RESERVADO);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}/{data}",cnpjRestaurante, LocalDate.of(2024, 3, 10))
				.content(UtilsTest.convertJson(parametrosBusca))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(reservasDisponiveisRestauranteDto)))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}
		
		@Test
		void naoDeveEncontrarReservasDoRestaurantePelaData() throws Exception {
			final String cnpjRestaurante = "71736952000116";
			
			final Restaurante restaurante = new RestauranteDto(
				cnpjRestaurante, 
				"Restaurante Denis Mock", 
				5, 
				TipoCozinha.JAPONESA, 
				Collections.emptyList(), null).toEntity();
			
			final ReservaDto reserva1 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 11, 8, 0));
			final ReservaDto reserva2 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 11, 9, 0));
			final ReservaDto reserva3 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 10, 10, 0));
			final ReservaDto reserva4 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 10, 10, 30));
			final ReservaDto reserva5 = criarReservaDataHoraPersonalizada(LocalDateTime.of(2024, 3, 12, 12, 0));
			final List<Reserva> reservasDisponiveisRestaurante = Arrays.asList(reserva1.toEntity(), reserva2.toEntity(), reserva3.toEntity(), reserva4.toEntity(), reserva5.toEntity());
			final ReservaDto parametrosBusca = new ReservaDto(null, null, cnpjRestaurante, null, SituacaoReserva.RESERVADO);
			
			when(restauranteService.getBuscarPor(any())).thenReturn(restaurante);
			
			when(repository.buscarTodasPor(restaurante)).thenReturn(
				reservasDisponiveisRestaurante);
			
			mockMvc.perform(MockMvcRequestBuilders
				.get("/reserva/restaurante/{cnpj}/{data}",cnpjRestaurante, LocalDate.of(2024, 3, 13))
				.content(UtilsTest.convertJson(parametrosBusca))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("[]"))
			//.andDo(MockMvcResultHandlers.print());
			;
			
			verify(restauranteService).getBuscarPor(any());
			verify(repository).buscarTodasPor(restaurante);
		}
		
		private ReservaDto criarReservaSituacaoPersonalizada(SituacaoReserva situacaoReserva) {
			return new ReservaDto(
				UUID.randomUUID(), 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				situacaoReserva
			);
		}
		
		private ReservaDto criarReservaDataHoraPersonalizada(LocalDateTime localDateTime) {
			return new ReservaDto(
				UUID.randomUUID(), 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				localDateTime, 
				SituacaoReserva.DISPONIVEL
			);
		}
	}
}