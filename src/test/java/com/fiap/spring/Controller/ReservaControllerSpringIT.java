package com.fiap.spring.Controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

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
	class ConcelarReserva{
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
					.statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
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
					.statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
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
		void deveBuscarReservasPeloUsuario() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			given()	
				.pathParam("email", emailUsuario)		
			.when()
				.get("/reserva/usuario/{email}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				;
		}
	}
}
