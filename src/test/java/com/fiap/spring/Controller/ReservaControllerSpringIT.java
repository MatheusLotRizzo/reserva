package com.fiap.spring.Controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.CriarReservaDTO;
import com.fiap.spring.Controller.Dto.ReservaDto;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;




@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReservaControllerSpringIT {

	@LocalServerPort
	private int porta;

	@BeforeEach
	void setUp() {
		RestAssured.port = porta;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	@Nested
	class CancelarReserva{
		@Test
		void deveCacenlarReserva() throws Exception {
			final String numeroReservaReservada = "fb93bb65-7e37-4990-8fb0-5a77e620a4ab";
			
			given()	
				.when()
					.patch("/reserva/cancelar/{numeroReserva}", numeroReservaReservada)
				.then()
					.statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT)
				;
		}
		
		@Test
		void naoDeveCancelarReservaCasoNaoEncontrada() throws Exception {
			final String numeroReservaReservada = "f589ae8b-6924-4ad0-9ced-d2e712058056";
			
			given()	
				.when()
					.patch("/reserva/cancelar/{numeroReserva}", numeroReservaReservada)
				.then()
					.statusCode(HttpStatus.SC_NOT_FOUND)
					.body("message", is("A Reserva informada, não é valida ou não foi encontrada"))
				;
		}
	}
	
	@Nested
	class ConcluirReserva{
		@Test
		void deveConcluirReserva() throws Exception {
			final String numeroReservaReservada = "cd5b81eb-1228-4c80-92e6-dc05b18d5e89";
			
			given()	
				.when()
					.patch("/reserva/concluir/{numeroReserva}", numeroReservaReservada)
				.then()
					.statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT)
				;
		}
		
		@Test
		void naoDeveConcluirReservaCasoNaoEncontrada() throws Exception {
			final String numeroReservaReservada = "f589ae8b-6924-4ad0-9ced-d2e712058056";
			
			given()	
				.when()
					.patch("/reserva/concluir/{numeroReserva}", numeroReservaReservada)
				.then()
					.statusCode(HttpStatus.SC_NOT_FOUND)
					.body("message", is("A Reserva informada, não é valida ou não foi encontrada"))
				;
		}
	}
	
	@Nested
	class CadastrarReserva{
		@Test
		void deveCriarReserva() throws Exception {
			final CriarReservaDTO reservaDto = new CriarReservaDTO(
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0) 
			);
			
			final ReservaDto retorno = given()	
				.body(reservaDto)
				.contentType(ContentType.JSON)
				.when()
					.post("/reserva")
				.then()
					.statusCode(org.apache.http.HttpStatus.SC_CREATED)
				.body("statusReserva", is(SituacaoReserva.RESERVADO.toString()))
				.extract().as(ReservaDto.class);
			
			assertNotNull(retorno.numeroReserva());
		}
		
		@Test
		void naoDeveCadastrarReservaCasoRestauranteNaoPossuaMesasDisponiveis() throws BusinessException {
			final CriarReservaDTO reservaDto = new CriarReservaDTO(
				"denis.benjamim@gmail.com", 
				"98376018000197", 
				LocalDateTime.of(2024, 3, 10, 12, 0)
			);
				
			given()	
				.body(reservaDto)
				.contentType(ContentType.JSON)
				.when()
					.post("/reserva")
				.then()
					.statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
				.body("message", is("Não existe disponibilidade para este dia"))
				;
		}
	}
	
	@Nested
	class BuscarReserva{
		@Test
		void deveBuscarReservasPeloEmailDoUsuario() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			given()	
				.pathParam("email", emailUsuario)		
			.when()
				.get("/reserva/usuario/{email}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				;
		}
		
		@Test
		void naoDeveBuscarReservasPeloEmailDoUsuario() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com.br";
			given()	
				.pathParam("email", emailUsuario)
			.when()
				.get("/reserva/usuario/{email}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND)
				.body("message", is("Usuário não encontrado!"))
				;
		}
		
		@Test
		void deveBuscarReservasPeloEmailDoUsuarioESituacaoDaReserva() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			final String situacaoReserva = SituacaoReserva.CONCLUIDO.name();
			final UUID uuid = UUID.fromString("de297c49-159b-49a1-bbb7-3339aee9eb14");
			
			final ReservaDto retorno = given()	
				.pathParam("email", emailUsuario)		
				.pathParam("situacao-reserva", situacaoReserva)	
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/usuario/{email}/{situacao-reserva}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.extract()
				.as(ReservaDto[].class)[0];
			
			assertEquals(uuid, retorno.numeroReserva());
			assertEquals("98376018000197", retorno.cnpjRestaurante());
			assertEquals(situacaoReserva, retorno.statusReserva().name());
		}
		
		@Test
		void deveBuscarReservasPeloCNPJDoRestaurante() throws Exception {
			final String cnpj = "71736952000116";
			
			given()	
				.pathParam("cnpj", cnpj)		
			.when()
				.get("/reserva/restaurante/{cnpj}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				;
		}
		
		@Test
		void naoDeveBuscarReservasPeloCNPJDoRestaurante() throws Exception {
			final String cnpj = "11906180000191";
			
			final ReservaDto[] retorno = given()	
				.pathParam("cnpj", cnpj)		
			.when()
				.get("/reserva/restaurante/{cnpj}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.extract()
				.as(ReservaDto[].class)
				;
			assertEquals(0, retorno.length);
		}
		
		@Test
		void deveBuscarReservasPeloCNPJDoRestauranteSituacaoDaReserva() throws Exception {
			final String cnpj = "71736952000116";
			final String situacaoReserva = SituacaoReserva.RESERVADO.name();
			
			final ReservaDto retorno = given()	
				.pathParam("cnpj", cnpj)		
				.pathParam("situacao-reserva", situacaoReserva)	
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/restaurante/{cnpj}/situacao/{situacao-reserva}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.extract()
				.as(ReservaDto[].class)[0];
			
			assertEquals("71736952000116", retorno.cnpjRestaurante());
			assertEquals(situacaoReserva, retorno.statusReserva().name());
		}
		
		@Test
		void naoDeveBuscarReservasPeloCNPJDoRestauranteSituacaoDaReserva() throws Exception {
			final String cnpj = "71736952000111";
			final String situacaoReserva = SituacaoReserva.RESERVADO.name();
			
			given()	
				.pathParam("cnpj", cnpj)		
				.pathParam("situacao-reserva", situacaoReserva)	
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/restaurante/{cnpj}/situacao/{situacao-reserva}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND)
				.body("message", is("Restaurante não encontrado"));
		}
		
		@Test
		void deveBuscarReservasPeloNumeroDaReserva() throws Exception {
			final String reserva = "cd5b81eb-1228-4c80-92e6-dc05b18d5e89";
			
			final ReservaDto retorno = given()	
				.pathParam("numeroReserva", reserva)		
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/{numeroReserva}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.extract()
				.as(ReservaDto.class);
			
			assertEquals("71736952000116", retorno.cnpjRestaurante());
			assertEquals(SituacaoReserva.RESERVADO, retorno.statusReserva());
		}
		
		@Test
		void naoDeveBuscarReservasPeloNumeroDaReserva() throws Exception {
			final String reserva = "cd5b81eb-1228-4c80-92e6-dc05b18d5e82";
			
			given()	
				.pathParam("numeroReserva", reserva)		
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/{numeroReserva}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND)
				.body("message", is("Reserva não encontrada"));
		}
		
		@Test
		void deveBuscarReservasPeloCNPJDoRestauranteDataDeReservas() throws Exception {
			final String cnpj = "71736952000116";
			
			final ReservaDto[] retorno = given()	
				.pathParam("cnpj", cnpj)		
				.pathParam("data", "2024-03-13")	
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/restaurante/{cnpj}/{data}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.extract()
				.as(ReservaDto[].class);
			
			assertEquals(2, retorno.length);
		}
		
		@Test
		void naoDeveBuscarReservasPeloCNPJDoRestauranteDataDeReservas() throws Exception {
			final String cnpj = "71736952000116";
			
			final ReservaDto[] retorno = given()	
				.pathParam("cnpj", cnpj)		
				.pathParam("data", "2024-03-11")	
				.contentType(ContentType.JSON)
			.when()
				.get("/reserva/restaurante/{cnpj}/{data}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.extract()
				.as(ReservaDto[].class);
			
			assertEquals(0, retorno.length);
		}
	}
}
